import axios from 'axios'
import {Alert, Button, Container, Tab, Tabs} from "react-bootstrap";
import {connect} from "react-redux";
import {BookingSummary} from "./BookFlight";
import {useLocation, useNavigate} from "react-router-dom";
import React, {Component, useEffect, useState} from "react";
import {bookFlights, register} from "../action/authActions";

class BookingFormOther extends Component {

    constructor(props) {
        super(props);
    }

    onFirstNameChange = (e) => {
        this.props.setFullBookingData(prevState => {
            const newFullBookingData = {...prevState}
            newFullBookingData[this.props.keyPrefix + this.props.index]['firstName'] = e.target.value
            return newFullBookingData
        })
    }

    onLastNameChange = (e) => {
        this.props.setFullBookingData(prevState => {
            const newFullBookingData = {...prevState}
            newFullBookingData[this.props.keyPrefix + this.props.index]['lastName'] = e.target.value
            return newFullBookingData
        })
    }

    render() {
        return (
            <div>
                <div className="text-center">
                    <input id="firstNameInput"
                           className={(('otherPassengers[' + this.props.index + '].firstName') in this.props.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                           placeholder="Imię" type="text" value={this.props.firstName}
                           onChange={this.onFirstNameChange}/>
                    {(('otherPassengers[' + this.props.index + '].firstName') in this.props.errors) ? (
                        <small id="firstNameHelp" className="text-danger mt-1">{this.props.errors['otherPassengers[' + this.props.index + '].firstName']}</small>
                    ) : null}
                    <input id="lastNameInput"
                           className={(('otherPassengers[' + this.props.index + '].lastName') in this.props.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                           placeholder="Nazwisko" type="text" value={this.props.lastName}
                           onChange={this.onLastNameChange}/>
                    {(('otherPassengers[' + this.props.index + '].lastName') in this.props.errors) ? (
                        <small id="lastNameHelp" className="text-danger mt-1">{this.props.errors['otherPassengers[' + this.props.index + '].lastName']}</small>
                    ) : null}
                    {('message' in this.props.errors) ? (
                        <div>
                            <small id="generalHelp" className="text-danger mt-1">{this.props.errors.message}</small>
                        </div>
                    ) : null}
                </div>
            </div>
        )
    }
}

class BookingFormMain extends Component {

    constructor(props) {
        super(props);
    }

    onFirstNameChange = (e) => {
        this.props.setFullBookingData(prevState => {
            const newFullBookingData = {...prevState}
            newFullBookingData[this.props.keyPrefix + this.props.index]['firstName'] = e.target.value
            return newFullBookingData
        })
    }

    onLastNameChange = (e) => {
        this.props.setFullBookingData(prevState => {
            const newFullBookingData = {...prevState}
            newFullBookingData[this.props.keyPrefix + this.props.index]['lastName'] = e.target.value
            return newFullBookingData
        })
    }

    onCityChange = (e) => {
        this.props.setFullBookingData(prevState => {
            const newFullBookingData = {...prevState}
            newFullBookingData[this.props.keyPrefix + this.props.index]['city'] = e.target.value
            return newFullBookingData
        })
    }

    onCountryChange = (e) => {
        this.props.setFullBookingData(prevState => {
            const newFullBookingData = {...prevState}
            newFullBookingData[this.props.keyPrefix + this.props.index]['country'] = e.target.value
            return newFullBookingData
        })
    }

    onPhoneNumberChange = (e) => {
        this.props.setFullBookingData(prevState => {
            const newFullBookingData = {...prevState}
            newFullBookingData[this.props.keyPrefix + this.props.index]['phoneNumber'] = e.target.value
            return newFullBookingData
        })
    }

    render() {
        return (
            <div className="text-center">
                <input id="firstNameInput"
                       className={('mainReservation.firstName' in this.props.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Imię" type="text" value={this.props.firstName} onChange={this.onFirstNameChange}/>
                {('mainReservation.firstName' in this.props.errors) ? (
                    <small id="firstNameHelp" className="text-danger mt-1">{this.props.errors['mainReservation.firstName']}</small>
                ) : null}
                <input id="lastNameInput"
                       className={('mainReservation.lastName' in this.props.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Nazwisko" type="text" value={this.props.lastName} onChange={this.onLastNameChange}/>
                {('mainReservation.lastName' in this.props.errors) ? (
                    <small id="lastNameHelp" className="text-danger mt-1">{this.props.errors['mainReservation.lastName']}</small>
                ) : null}
                <input id="cityInput"
                       className={('mainReservation.city' in this.props.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Miasto" type="text" value={this.props.city} onChange={this.onCityChange} />
                {('mainReservation.city' in this.props.errors) ? (
                    <small id="cityHelp" className="text-danger mt-1">{this.props.errors['mainReservation.city']}</small>
                ) : null}
                <input id="countryInput"
                       className={('mainReservation.country' in this.props.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Kraj" type="text" value={this.props.country} onChange={this.onCountryChange} />
                {('mainReservation.country' in this.props.errors) ? (
                    <small id="countryHelp" className="text-danger mt-1">{this.props.errors['mainReservation.country']}</small>
                ) : null}
                <input id="phoneNumberInput"
                       className={('mainReservation.phoneNumber' in this.props.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"}
                       placeholder="Numer telefonu" type="text" value={this.props.phoneNumber} onChange={this.onPhoneNumberChange} />
                {('mainReservation.phoneNumber' in this.props.errors) ? (
                    <small id="phoneNumberHelp" className="text-danger mt-1">{this.props.errors['mainReservation.phoneNumber']}</small>
                ) : null}
                {('message' in this.props.errors) ? (
                    <div>
                        <small id="generalHelp" className="text-danger mt-1">{this.props.errors.message}</small>
                    </div>
                ) : null}
            </div>
        )
    }
}

const BookingForm = (props) => {
    const location = useLocation()
    const bookedSeats = location.state.bookedSeats
    const flightData = location.state.flightData
    const adultsKeys = Object.keys(bookedSeats).filter(seat => seat.startsWith('adult') && seat !== 'adult0')
    const adultsBookings = adultsKeys.reduce((newObj, key) => {
        newObj[key] = bookedSeats[key]
        return newObj
    }, {})
    const adults = adultsKeys.length
    const childrenKeys = Object.keys(bookedSeats).filter(seat => seat.startsWith('child'))
    const childrenBookings = childrenKeys.reduce((newObj, key) => {
        newObj[key] = bookedSeats[key]
        return newObj
    }, {})
    const children = childrenKeys.length
    const [fullBookingData, setFullBookingData] = useState({})
    const [errors, setErrors] = useState({})
    const [isLoading, setIsLoading] = useState(false)
    const navigate = useNavigate()
    const [notLoggedAlertVisible, setNotLoggedAlertVisible] = useState(false)

    useEffect(() => {
        const currentBookingData = {...fullBookingData}
        currentBookingData['adult0'] = {bookedSeat: bookedSeats['adult0']} // TODO fill main passenger's form with logged in user data
        for (let i = 1; i < adults; i++) {
            currentBookingData[('adult' + i)] = {bookedSeat: bookedSeats[('adult' + i)]}
        }
        for (let i = 0; i < children; i++) {
            currentBookingData[('child' + i)] = {bookedSeat: bookedSeats[('child' + i)]}
        }
        setFullBookingData(currentBookingData)
    }, []);

    function createRequest(fullBookingData) {
        let request = {
            flightId: location.state.flightId,
        }

        const mainReservationData = fullBookingData['adult0']
        request['mainReservation'] = {
            rowNumber: mainReservationData.bookedSeat.match(/^(\d+)/) ? Number(fullBookingData['adult0'].bookedSeat.match(/^(\d+)/)[0]) : null,
            seatLetter: mainReservationData.bookedSeat.replace(/^(\d+)/, ""),
            firstName: mainReservationData.firstName || '',
            lastName: mainReservationData.lastName || '',
            city: mainReservationData.city || '',
            country: mainReservationData.country || '',
            phoneNumber: mainReservationData.phoneNumber || ''
        }

        let otherPassengersData = []
        const otherBookingData = Object.keys(fullBookingData).filter(key => key !== 'adult0').reduce((newObj, key) => {
            newObj[key] = fullBookingData[key]
            return newObj
        }, {})

        for (let otherBookingDataKey in otherBookingData) {
            const bookingData = otherBookingData[otherBookingDataKey]
            const ageGroup = otherBookingDataKey.startsWith("child") ? "CHILD" : "ADULT"
            const finalBookingData = {
                rowNumber: bookingData.bookedSeat.match(/^(\d+)/) ? Number(fullBookingData['adult0'].bookedSeat.match(/^(\d+)/)[0]) : null,
                seatLetter: bookingData.bookedSeat.replace(/^(\d+)/, ""),
                ageGroup,
                firstName: bookingData.firstName || '',
                lastName: bookingData.lastName || ''
            }
            otherPassengersData.push(finalBookingData)
        }
        request['otherPassengers'] = otherPassengersData
        return request
    }

    function makeReservation(fullBookingData, navigate) {
        const request = createRequest(fullBookingData)
        setErrors({})
        setIsLoading(true)
        setNotLoggedAlertVisible(false)
        bookFlights(request).then(res => {
            navigate("/", {state: {alertType: 'alert-success', alertMessage: "Pomyślnie zarezerwowano lot. Sprawdź swoją skrzynkę odbiorczą email"}})
        }).catch(err => {
            if(err.status === 403) {
                setNotLoggedAlertVisible(true)
            }
            setErrors(err.data)
        }).finally(() => {
            setIsLoading(false)
        })
    }

    return (
        <Container>
            {notLoggedAlertVisible ? (
                <Alert className="text-center" key="notLoggedIn" variant="danger">
                    Nie jesteś zalogowany! Zaloguj się, aby zarezerwować lot.
                </Alert>
            ) : null}
            <BookingSummary className="mt-3" bookedSeats={bookedSeats} flightData={flightData}/>
            <Tabs
                defaultActiveKey="adult0"
                id="passengers-form"
                className="mb-3 mt-3">
                <Tab eventKey="adult0" title="Dorosły 1 (główny)">
                    <BookingFormMain index={0} bookings={adultsBookings} setFullBookingData={setFullBookingData}
                                     keyPrefix="adult" errors={errors}/>
                </Tab>
                {[...Array(adults).keys()].map((adultIndex, i) => {
                    return (
                        <Tab eventKey={"adult" + (Number(adultIndex) + 1)}
                             title={"Dorosły " + (Number(adultIndex) + 2)}>
                            <BookingFormOther index={Number(adultIndex + 1)} bookings={adultsBookings}
                                              setFullBookingData={setFullBookingData} keyPrefix="adult"
                                              errors={errors}/>
                        </Tab>
                    )
                })}
                {[...Array(children).keys()].map((childIndex, i) => {
                    return (
                        <Tab eventKey={"child" + (Number(childIndex))} title={"Dziecko " + (Number(childIndex) + 1)}>
                            <BookingFormOther index={Number(childIndex)} bookings={childrenBookings}
                                              setFullBookingData={setFullBookingData} keyPrefix="child"
                                              errors={errors}/>
                        </Tab>
                    )
                })}
            </Tabs>
            <Button className="mx-auto d-block mt-3" variant="primary" disabled={isLoading}
                    onClick={!isLoading ? () => makeReservation(fullBookingData, navigate) : null}>Dalej</Button>
        </Container>
    )
}

const mapStateToProps = (state) => {
    return {currentUser: state.auth.currentUser}
}

export default connect(mapStateToProps)(BookingForm)