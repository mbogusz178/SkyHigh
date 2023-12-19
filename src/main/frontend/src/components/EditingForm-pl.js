import axios from 'axios'
import {Alert, Button, Container, Tab, Tabs} from "react-bootstrap";
import {connect} from "react-redux";
import {BookingSummary, SeatRow} from "./BookFlight";
import {useLocation, useNavigate} from "react-router-dom";
import React, {Component, useEffect, useState} from "react";
import {register} from "../action/authActions";
import {bookFlights, editFlight} from "../action/flightActions";

class EditingFormOther extends Component {

    constructor(props) {
        super(props);
        this.state = {
            errors: {},
            isLoading: false,
            fullBookingData: {}
        }
    }

    onFirstNameChange = (e) => {
        const newFullBookingData = {...this.state.fullBookingData}
        newFullBookingData[this.props.keyPrefix + this.props.index]['firstName'] = e.target.value
        this.setState({fullBookingData: newFullBookingData})
    }

    onLastNameChange = (e) => {
        const newFullBookingData = {...this.state.fullBookingData}
        newFullBookingData[this.props.keyPrefix + this.props.index]['lastName'] = e.target.value
        this.setState({fullBookingData: newFullBookingData})
    }

    componentDidMount() {
        const currentBookingData = {...this.state.fullBookingData}
        const currentKey = this.props.keyPrefix + this.props.index
        currentBookingData[currentKey] = {bookedSeat: this.props.bookedSeats[currentKey]} // TODO fill main passenger's form with logged in user data
        this.setState({fullBookingData: currentBookingData})
    }

    createRequest = (fullBookingData) => {
        let request = {
            ticketId: this.props.flight.passengers[this.props.totalIndex],
        }

        const reservationData = fullBookingData[this.props.keyPrefix + this.props.index]
        request['rowNumber'] = reservationData.bookedSeat.match(/^(\d+)/) ? Number(fullBookingData['adult0'].bookedSeat.match(/^(\d+)/)[0]) : null
        request['seatLetter'] = reservationData.bookedSeat.replace(/^(\d+)/, "")
        request['firstName'] = reservationData.firstName
        request['lastName'] = reservationData.lastName

        return request
    }

    editReservation = (fullBookingData, navigate) => {
        const request = this.createRequest(fullBookingData)
        this.setState({errors: {}, isLoading: true})
        this.props.setNotLoggedAlertVisible(false)
        editFlight(request).then(res => {
            navigate("/bookedFlights", {state: {alertType: 'alert-success', alertMessage: "Pomyślnie edytowano lot"}})
        }).catch(err => {
            if(err.status === 403) {
                this.props.setNotLoggedAlertVisible(true)
            }
            this.setState({errors: err.data})
        }).finally(() => {
            this.setState({isLoading: false})
        })
    }

    render() {
        const passengerKey = this.props.keyPrefix + this.props.index
        console.log(this.props.keyPrefix)
        console.log(this.props.index)
        return (
            <div>
                <div className="text-center">
                    <input id="firstNameInput"
                           className={(('otherPassengers[' + this.props.totalIndex + '].firstName') in this.props.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                           placeholder="Imię" type="text" value={this.props.passengersNames[passengerKey].firstName}
                           onChange={this.onFirstNameChange}/>
                    {(('otherPassengers[' + this.props.totalIndex + '].firstName') in this.props.errors) ? (
                        <small id="firstNameHelp" className="text-danger mt-1">{this.props.errors['otherPassengers[' + this.props.totalIndex + '].firstName']}</small>
                    ) : null}
                    <input id="lastNameInput"
                           className={(('otherPassengers[' + this.props.totalIndex + '].lastName') in this.props.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                           placeholder="Nazwisko" type="text" value={this.props.passengersNames[passengerKey].lastName}
                           onChange={this.onLastNameChange}/>
                    {(('otherPassengers[' + this.props.totalIndex + '].lastName') in this.props.errors) ? (
                        <small id="lastNameHelp" className="text-danger mt-1">{this.props.errors['otherPassengers[' + this.props.totalIndex + '].lastName']}</small>
                    ) : null}
                    {('message' in this.props.errors) ? (
                        <div>
                            <small id="generalHelp" className="text-danger mt-1">{this.props.errors.message}</small>
                        </div>
                    ) : null}
                    <Button className="mx-auto d-block mt-3" variant="primary" disabled={this.state.isLoading}
                            onClick={!this.state.isLoading ? () => this.editReservation(this.state.fullBookingData, this.props.navigate) : null}>Dalej</Button>
                </div>
            </div>
        )
    }
}

class EditingFormMain extends Component {

    constructor(props) {
        super(props);
        this.state = {
            errors: {},
            fullBookingData: {
                firstName: this.props.passengersNames[this.props.keyPrefix + this.props.index],
                lastName: this.props.passengersNames[this.props.keyPrefix + this.props.index],
                city: this.props.currentUser.city,
                country: this.props.currentUser.country
            },
            isLoading: false
        }
    }

    componentDidMount() {
        const currentBookingData = {...this.state.fullBookingData}
        currentBookingData['adult0'] = {bookedSeat: this.props.bookedSeats['adult0']} // TODO fill main passenger's form with logged in user data
        this.setState({fullBookingData: currentBookingData})
    }

    onFirstNameChange = (e) => {
        const newFullBookingData = {...this.state.fullBookingData}
        newFullBookingData[this.props.keyPrefix + this.props.index]['firstName'] = e.target.value
        this.setState({fullBookingData: newFullBookingData})
    }

    onLastNameChange = (e) => {
        const newFullBookingData = {...this.state.fullBookingData}
        newFullBookingData[this.props.keyPrefix + this.props.index]['lastName'] = e.target.value
        this.setState({fullBookingData: newFullBookingData})
    }

    onCityChange = (e) => {
        const newFullBookingData = {...this.state.fullBookingData}
        newFullBookingData[this.props.keyPrefix + this.props.index]['city'] = e.target.value
        this.setState({fullBookingData: newFullBookingData})
    }

    onCountryChange = (e) => {
        const newFullBookingData = {...this.state.fullBookingData}
        newFullBookingData[this.props.keyPrefix + this.props.index]['country'] = e.target.value
        this.setState({fullBookingData: newFullBookingData})
    }

    onPhoneNumberChange = (e) => {
        const newFullBookingData = {...this.state.fullBookingData}
        newFullBookingData[this.props.keyPrefix + this.props.index]['phoneNumber'] = e.target.value
        this.setState({fullBookingData: newFullBookingData})
    }

    createRequest = (fullBookingData) => {
        let request = {
            ticketId: this.props.flight.passengers[this.props.totalIndex],
        }

        const mainReservationData = this.props.bookedSeats['adult0']
        request['rowNumber'] = mainReservationData.bookedSeat.match(/^(\d+)/) ? Number(fullBookingData['adult0'].bookedSeat.match(/^(\d+)/)[0]) : null
        request['seatLetter'] = mainReservationData.bookedSeat.replace(/^(\d+)/, "")
        request['firstName'] = mainReservationData.firstName
        request['lastName'] = mainReservationData.lastName

        return request
    }

    editReservation = (fullBookingData, navigate) => {
        const request = this.createRequest(fullBookingData)
        this.setState({errors: {}, isLoading: true})
        this.props.setNotLoggedAlertVisible(false)
        editFlight(request).then(res => {
            navigate("/bookedFlights", {state: {alertType: 'alert-success', alertMessage: "Pomyślnie edytowano lot"}})
        }).catch(err => {
            if(err.status === 403) {
                this.props.setNotLoggedAlertVisible(true)
            }
            this.setState({errors: err.data})
        }).finally(() => {
            this.setState({isLoading: false})
        })
    }

    render() {
        const passengerKey = this.props.keyPrefix + this.props.index
        return (
            <div className="text-center">
                <input id="firstNameInput"
                       className={('mainReservation.firstName' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Imię" type="text" value={this.state.fullBookingData.firstName} onChange={this.onFirstNameChange}/>
                {('mainReservation.firstName' in this.state.errors) ? (
                    <small id="firstNameHelp" className="text-danger mt-1">{this.state.errors['mainReservation.firstName']}</small>
                ) : null}
                <input id="lastNameInput"
                       className={('mainReservation.lastName' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Nazwisko" type="text" value={this.state.fullBookingData.lastName} onChange={this.onLastNameChange}/>
                {('mainReservation.lastName' in this.state.errors) ? (
                    <small id="lastNameHelp" className="text-danger mt-1">{this.state.errors['mainReservation.lastName']}</small>
                ) : null}
                <input id="cityInput"
                       className={('mainReservation.city' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Miasto" type="text" value={this.props.currentUser.city} onChange={this.onCityChange} />
                {('mainReservation.city' in this.state.errors) ? (
                    <small id="cityHelp" className="text-danger mt-1">{this.state.errors['mainReservation.city']}</small>
                ) : null}
                <input id="countryInput"
                       className={('mainReservation.country' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Kraj" type="text" value={this.props.currentUser.country} onChange={this.onCountryChange} />
                {('mainReservation.country' in this.state.errors) ? (
                    <small id="countryHelp" className="text-danger mt-1">{this.state.errors['mainReservation.country']}</small>
                ) : null}
                <input id="phoneNumberInput"
                       className={('mainReservation.phoneNumber' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Numer telefonu" type="text" value={this.props.phoneNumber} onChange={this.onPhoneNumberChange} />
                {('mainReservation.phoneNumber' in this.state.errors) ? (
                    <small id="phoneNumberHelp" className="text-danger mt-1">{this.state.errors['mainReservation.phoneNumber']}</small>
                ) : null}
                {('message' in this.state.errors) ? (
                    <div>
                        <small id="generalHelp" className="text-danger mt-1">{this.state.errors.message}</small>
                    </div>
                ) : null}
                <Button className="mx-auto d-block mt-3" variant="primary" disabled={this.state.isLoading}
                        onClick={!this.state.isLoading ? () => this.editReservation(this.state.fullBookingData, this.props.navigate) : null}>Dalej</Button>
            </div>
        )
    }
}

const EditingSeats = (props) => {

    const adults = props.adults
    const children = props.children
    const flightData = props.flightData
    const setBookedSeats = props.setBookedSeats
    const bookedSeats = props.bookedSeats

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
            {((props.flightData !== null) && (rowConfigArray !== null)) ? (
                <div>
                    <h5 className="text-center">Zmiana rezerwacji lotu z {props.flight.source.city} do {props.flight.destination.city} (samolot {flightData.planeModel})</h5>
                    <p className="text-center">Kliknij w miejsce, aby przydzelić do niego jednego z pasażerów. Wolne miejsca oznaczone są kolorem niebieskim, niedostępne - szarym, zaś zajęte - paskami.</p>
                    <div className="container mt-3">
                        {[...Array(numRows).keys()].map((row, i) => {
                            console.log(adults)
                            console.log(children)
                            const sortedFiltered = seatList.filter((seat) => seat.rowNumber === row + 1).sort((a, b) => a.seatLetter.localeCompare(b.seatLetter))
                            let columnCount = 0
                            return (
                                <div className="text-center row mt-3 justify-content-center">
                                    {rowConfigArray.map((columnGroup, i) => {
                                        const columnCount = rowConfigArray.filter((colGroup, index) => index < i).reduce((partial, a) => partial + a, 0)
                                        return <SeatRow columnGroup={columnGroup} numColumns={numColumns} sortedFiltered={sortedFiltered} columnIndex={columnCount} adults={adults} children={children} setBookedSeats={setBookedSeats} bookedSeats={bookedSeats} />
                                    })}
                                </div>
                            )
                        })}
                        <BookingSummary flightData={props.flightData} bookedSeats={props.bookedSeats}/>
                    </div>
                </div>
            ) : null}
        </div>
    )
}

const EditingForm = (props) => {
    const location = useLocation()
    const navigate = useNavigate()
    const [flightData, setFlightData] = useState(null)
    const [bookedSeats, setBookedSeats] = useState(null)
    const [passengersNames, setPassengersNames] = useState(null)
    const flight = location.state.flight

    let adultsKeys = null
    let adultsBookings = null
    let adults = null;
    let childrenKeys = null
    let childrenBookings = null
    let children = null
    let [notLoggedAlertVisible, setNotLoggedAlertVisible] = useState(false)

    if(bookedSeats && passengersNames && flightData) {
        adultsKeys = Object.keys(bookedSeats).filter(seat => seat.startsWith('adult') && seat !== 'adult0')
        adultsBookings = adultsKeys.reduce((newObj, key) => {
            newObj[key] = bookedSeats[key]
            return newObj
        }, {})
        adults = adultsKeys.length
        console.log(adultsKeys)
        console.log(adults)
        childrenKeys = Object.keys(bookedSeats).filter(seat => seat.startsWith('child'))
        childrenBookings = childrenKeys.reduce((newObj, key) => {
            newObj[key] = bookedSeats[key]
            return newObj
        }, {})
        children = childrenKeys.length
        console.log(childrenKeys)
        console.log(children)
    }

    useEffect(() => {
        axios.get("http://localhost/api/flights/" + flight.id + "/bookingData").then(res => setFlightData(res.data))
    }, []);

    useEffect(() => {
        let adultIndex = 0
        let childIndex = 0
        const newBookedSeats = {}
        const newPassengersNames = {}
        flight.passengers.forEach(passenger => {
            if(passenger.ageGroup === 'ADULT') {
                console.log('adult added')
                newBookedSeats['adult' + adultIndex] = (passenger.rowNumber.toString() + passenger.seatLetter)
                newPassengersNames['adult' + adultIndex++] = {firstName: passenger.firstName, lastName: passenger.lastName}
            }
            else if(passenger.ageGroup === 'CHILD') {
                console.log('child added')
                newBookedSeats['child' + childIndex] = (passenger.rowNumber.toString() + passenger.seatLetter)
                newPassengersNames['child' + childIndex++] = {firstName: passenger.firstName, lastName: passenger.lastName}
            }
        })
        setBookedSeats(newBookedSeats)
        setPassengersNames(newPassengersNames)
    }, []);

    return (
        <Container>
            {notLoggedAlertVisible ? (
                <Alert className="text-center" key="notLoggedIn" variant="danger">
                    Nie jesteś zalogowany! Zaloguj się, aby zarezerwować lot.
                </Alert>
            ) : null}
            {(bookedSeats && flightData && passengersNames) ? (
                <div>
                    <EditingSeats className="mt-3" flight={flight} flightData={flightData} bookedSeats={bookedSeats} setBookedSeats={setBookedSeats} adults={adults + 1} children={children}/>
                    <Tabs
                        defaultActiveKey="adult0"
                        id="passengers-form"
                        className="mb-3 mt-3">
                        <Tab eventKey="adult0" title="Dorosły 1 (główny)">
                            <EditingFormMain index={0} bookings={adultsBookings} bookedSeats={bookedSeats} totalIndex={0} currentUser={props.currentUser} flight={flight}
                                             keyPrefix="adult" setNotLoggedAlertVisible={setNotLoggedAlertVisible} passengersNames={passengersNames}/>
                        </Tab>
                        {[...Array(adults).keys()].map((adultIndex, i) => {
                            return (
                                <Tab eventKey={"adult" + (Number(adultIndex) + 1)}
                                     title={"Dorosły " + (Number(adultIndex) + 2)}>
                                    <EditingFormOther index={Number(adultIndex + 1)} bookings={adultsBookings} totalIndex={Number(adultIndex + 1)} flight={flight} flightData={flightData}
                                                      keyPrefix="adult" bookedSeats={bookedSeats} navigate={navigate} setNotLoggedAlertVisible={setNotLoggedAlertVisible} passengersNames={passengersNames}/>
                                </Tab>
                            )
                        })}
                        {[...Array(children).keys()].map((childIndex, i) => {
                            return (
                                <Tab eventKey={"child" + (Number(childIndex))} title={"Dziecko " + (Number(childIndex) + 1)}>
                                    <EditingFormOther index={Number(childIndex)} bookings={childrenBookings} totalIndex={adults + Number(childIndex)} flight={flight} flightData={flightData}
                                                      keyPrefix="child" bookedSeats={bookedSeats} navigate={navigate} setNotLoggedAlertVisible={setNotLoggedAlertVisible} passengersNames={passengersNames}/>
                                </Tab>
                            )
                        })}
                    </Tabs>
                </div>
            ) : null}
        </Container>
    )
}

const mapStateToProps = (state) => {
    return {currentUser: state.auth.currentUser}
}

export default connect(mapStateToProps)(EditingForm)