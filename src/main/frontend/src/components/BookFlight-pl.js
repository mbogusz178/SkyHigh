import axios from 'axios'
import {connect} from "react-redux";
import {useLocation, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {Button, Card, Dropdown} from "react-bootstrap";

export const BookingPassengerData = (props) => {
    const ageGroup = props.ageGroup === 'adult' ? 'Dorosły' : 'Dziecko'
    const ageGroupIndex = props.ageGroupIndex + 1
    const theSeat = props.flightData.seats.find(seat => {
        return (seat.rowNumber === props.rowNumber) && (seat.seatLetter === props.seatLetter)
    })

    return (
        <Card className="col col-auto" text={"white"} bg={"primary"}>
            <Card.Header>{ageGroup} {ageGroupIndex}</Card.Header>
            <Card.Body>
                <Card.Text>
                    Numer rzędu: {theSeat.rowNumber} <br/>
                    Litera siedzenia: {theSeat.seatLetter} <br/>
                    Klasa lotu: {theSeat.flightClassName}
                </Card.Text>
            </Card.Body>
        </Card>
    )
}

export const BookingSummary = (props) => {
    return (
        <div className="row justify-content-center mt-3">
            {Object.keys(props.bookedSeats).map((key, index) => {
                const ageGroup = key.replace(/\d+$/, "")
                const ageGroupIndex = key.match(/\d+$/) ? Number(key.match(/\d+$/)[0]) : null
                const rowNumber = props.bookedSeats[key].match(/^(\d+)/) ? Number(props.bookedSeats[key].match(/^(\d+)/)[0]) : null
                const seatLetter = props.bookedSeats[key].replace(/^(\d+)/, "")
                return (
                    <BookingPassengerData flightData={props.flightData} ageGroup={ageGroup} ageGroupIndex={ageGroupIndex} rowNumber={rowNumber} seatLetter={seatLetter}/>
                )
            })}
        </div>
    )
}

const SeatText = React.forwardRef(({children, onClick}, ref) => (
    <span ref={ref} onClick={(e) => {
        e.preventDefault()
        onClick(e)
    }}>{children}</span>
))

export const SeatRow = (props) => {
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
        const childArray = [...Array(props.children).keys()]
        const columnGroupArray = [...Array(props.columnGroup).keys()]

        return (
            <Dropdown className={"col col-auto " + ((props.columnIndex !== 0 && columnIndexInGroup === 0) ? "offset-1 " : "") + "flight-class-" + theSeat.flightClass.toLowerCase() + seatStatus}>
                <Dropdown.Toggle as={SeatText} id="dropdown-booking">
                    {theSeat.rowNumber}{theSeat.seatLetter}
                </Dropdown.Toggle>
                {(theSeat.status !== 'BOOKED' && theSeat.status !== 'UNAVAILABLE') ? (
                    <Dropdown.Menu>
                        {adultArray.map((e, i) => {
                            return (
                                <Dropdown.Item key={i} onClick={() => selectForAdult(i, theSeat.rowNumber, theSeat.seatLetter)}>Dorosły {(i + 1).toString()}</Dropdown.Item>
                            )
                        })}
                        {childArray.map((e, i) => {
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
    const navigate = useNavigate()

    function redirectToForm(bookedSeats) {
        navigate("/bookingForm", { state: {bookedSeats, flightId: flight.id, flightData} })
    }

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
                    <h5 className="text-center">Rezerwacja lotu z {flight.source.city} do {flight.destination.city} (samolot {flightData.planeModel})</h5>
                    <p className="text-center">Kliknij w miejsce, aby przydzelić do niego jednego z pasażerów. Wolne miejsca oznaczone są kolorem niebieskim, niedostępne - szarym, zaś zajęte - paskami.</p>
                    <div className="container mt-3">
                        {[...Array(numRows).keys()].map((row, i) => {
                            const sortedFiltered = seatList.filter((seat) => seat.rowNumber === row + 1).sort((a, b) => a.seatLetter.localeCompare(b.seatLetter))
                            let columnCount = 0
                            return (
                                <div className="text-center row mt-3 justify-content-center">
                                    {rowConfigArray.map((columnGroup, i) => {
                                        const columnCount = rowConfigArray.filter((colGroup, index) => index < i).reduce((partial, a) => partial + a, 0)
                                        return <SeatRow columnGroup={columnGroup} numColumns={numColumns} sortedFiltered={sortedFiltered} columnIndex={columnCount} adults={adults} children={children} setBookedSeats={setBookedSeats} bookedSeats={bookedSeats}/>
                                    })}
                                </div>
                            )
                        })}
                        <BookingSummary flightData={flightData} bookedSeats={bookedSeats}/>
                        <Button className="mx-auto d-block mt-3" variant="primary" disabled={Object.keys(bookedSeats).length < (adults + children)} onClick={(Object.keys(bookedSeats).length >= (adults + children)) ? () => redirectToForm(bookedSeats) : null}>Dalej</Button>
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