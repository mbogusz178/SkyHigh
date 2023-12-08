import axios from 'axios'
import {connect} from "react-redux";
import {useLocation} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {Dropdown, DropdownItem, DropdownMenu} from "react-bootstrap";

const SeatText = React.forwardRef(({children, onClick}, ref) => (
    <span ref={ref} onClick={(e) => {
        e.preventDefault()
        onClick(e)
    }}>{children}</span>
))

const SeatRow = (props) => {
    function selectForAdult(i, rowNumber, seatLetter) {
        const newBookedSeats = {...props.bookedSeats}
        newBookedSeats['adult' + i] = rowNumber.toString() + seatLetter.toString()
        props.setBookedSeats(newBookedSeats)
    }

    function selectForChild(i, rowNumber, seatLetter) {
        const newBookedSeats = {...props.bookedSeats}
        newBookedSeats['child' + i] = rowNumber.toString() + seatLetter.toString()
        props.setBookedSeats(newBookedSeats)
    }

    return [...Array(props.columnGroup).keys()].map((columnIndexInGroup, i) => {
        const theSeat = props.sortedFiltered[props.columnIndex + columnIndexInGroup]
        let seatStatus = ''
        if(theSeat.status === 'BOOKED') {
            seatStatus = ' seat-booked'
        } else if(theSeat.status === 'UNAVAILABLE') {
            seatStatus = ' seat-unavailable'
        } else if(Object.values(props.bookedSeats).indexOf(theSeat.rowNumber.toString() + theSeat.seatLetter.toString()) > -1) {
            seatStatus = ' seat-chosen'
        }

        const adultArray = [...Array(props.adults).keys()]
        console.log(props.adults)
        console.log(adultArray)
        const childArray = [...Array(props.children).keys()]
        console.log(props.children)
        console.log(childArray)
        const columnGroupArray = [...Array(props.columnGroup).keys()]
        console.log(props.columnGroup)
        console.log(columnGroupArray)

        return (
            <Dropdown className={"col col-auto " + ((props.columnIndex !== 0 && columnIndexInGroup === 0) ? "offset-1 " : "") + "flight-class-" + theSeat.flightClass.toLowerCase() + seatStatus}>
                <Dropdown.Toggle as={SeatText} id="dropdown-booking">
                    {theSeat.rowNumber}{theSeat.seatLetter}
                </Dropdown.Toggle>
                {(theSeat.status !== 'BOOKED' && theSeat.status !== 'UNAVAILABLE') ? (
                    <Dropdown.Menu>
                        {adultArray.map((e, i) => {
                            console.log(i)
                            return (
                                <Dropdown.Item key={i} onClick={() => selectForAdult(i, theSeat.rowNumber, theSeat.seatLetter)}>Dorosły {(i + 1).toString()}</Dropdown.Item>
                            )
                        })}
                        {childArray.map((e, i) => {
                            console.log(props.adults + i)
                            return (
                                <Dropdown.Item key={props.adults + i} onClick={() => selectForChild(i, theSeat.rowNumber, theSeat.seatLetter)}>Dziecko {(i + 1).toString()}</Dropdown.Item>
                            )
                        })}
                    </Dropdown.Menu>
                ) : null}
            </Dropdown>
        )
    })
}

const BookFlight = (props) => {
    const location = useLocation()
    const [flightData, setFlightData] = useState(null)
    const [bookedSeats, setBookedSeats] = useState({})
    const flight = location.state.flight

    useEffect(() => {
        axios.get("http://localhost/api/flights/" + flight.id + "/bookingData").then(res => setFlightData(res.data))
    }, [])

    let adults = 1
    let children = 0

    if('state' in location) {
        if('adults' in location.state) {
            adults = Number(location.state.adults)
        }
        if('children' in location.state) {
            children = Number(location.state.children)
        }
    }

    let rowConfigArray = null
    let numRows = null
    let numColumns = null
    let seatList = null

    if(flightData !== null) {
        try {
            rowConfigArray = flightData.seatConfig.rowConfig.split('-').map(a => Number(a))
            numRows = flightData.seatConfig.numRows
            numColumns = flightData.seatConfig.numColumns
            seatList = flightData.seats
        } catch (e) {
            console.log(e)
        }
    }

    return (
        <div>
            {((flightData !== null) && (rowConfigArray !== null)) ? (
                <div>
                    <p className="text-center">Rezerwacja lotu z {flight.source.city} do {flight.destination.city} (samolot {flightData.planeModel})</p>
                    <div className="container text-center mt-3">
                        {[...Array(numRows).keys()].map((row, i) => {
                            const sortedFiltered = seatList.filter((seat) => seat.rowNumber === row + 1).sort((a, b) => a.seatLetter.localeCompare(b.seatLetter))
                            let columnCount = 0
                            return (
                                <div className="row mt-3 justify-content-center">
                                    {rowConfigArray.map((columnGroup, i) => {
                                        const columnCount = rowConfigArray.filter((colGroup, index) => index < i).reduce((partial, a) => partial + a, 0)
                                        return <SeatRow columnGroup={columnGroup} numColumns={numColumns} sortedFiltered={sortedFiltered} columnIndex={columnCount} adults={adults} children={children} setBookedSeats={setBookedSeats} bookedSeats={bookedSeats}/>
                                    })}
                                </div>
                            )
                        })}
                    </div>
                </div>
            ) : null}
        </div>
    )
}

const mapStateToProps = (state) => {
    return {currentUser: state.auth.currentUser}
}

export default connect(mapStateToProps)(BookFlight)