import axios from 'axios'
import {connect} from "react-redux";
import {Button, Container} from "react-bootstrap";
import {getEnglishMonth} from "./FlightListing";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

const UserBookedFlightListing = (props) => {
    const navigate = useNavigate()
    const flight = props.flight
    const departureDate = new Date(flight.departureDate)
    const arrivalDate = new Date(flight.arrivalDate)
    const [isConfirmationDisabled, setIsConfirmationDisabled] = useState((departureDate.getTime() - 1800000 > new Date()) || flight.confirmed || flight.cancelled)
    const [isCancellingDisabled, setIsCancellingDisabled] = useState(flight.confirmed || flight.cancelled)
    const [isRequestPending, setIsRequestPending] = useState(false)

    function updateDisabled() {
        setIsConfirmationDisabled((departureDate.getTime() - 1800000 > new Date()) || flight.confirmed || flight.cancelled)
        setIsCancellingDisabled(flight.confirmed || flight.cancelled)
        props.updateFlights()
    }
    function confirmFlight() {
        setIsRequestPending(true);
        axios.post('http://localhost/api/flights/confirmFlight', {value: flight.id}).then(res => {
            navigate("/bookedFlights", {state: {alertType: 'alert-success', alertMessage: res.data}})
        }).catch(err => {
            if(err.response.status === 403) {
                navigate("/login", {state: {alertType: 'alert-danger', alertMessage: err.response.data}})
            } else {
                navigate("/bookedFlights", {state: {alertType: 'alert-danger', alertMessage: err.response.data}})
            }
        }).finally(() => {
            setIsRequestPending(false)
            updateDisabled()
        })
    }

    function cancelFlight() {
        setIsRequestPending(true);
        axios.post('http://localhost/api/flights/cancelFlight', {value: flight.id}).then(res => {
            navigate("/bookedFlights", {state: {alertType: 'alert-success', alertMessage: res.data}})
        }).catch(err => {
            if(err.response.status === 403) {
                navigate("/login", {state: {alertType: 'alert-danger', alertMessage: err.response.data}})
            } else {
                navigate("/bookedFlights", {state: {alertType: 'alert-danger', alertMessage: err.response.data}})
            }
        }).finally(() => {
            setIsRequestPending(false)
            updateDisabled()
        })
    }

    return (
        <tr className="table-primary">
            <td>{flight.source.city} ({flight.source.id})</td>
            <td>{flight.destination.city} ({flight.destination.id})</td>
            <td>{getEnglishMonth(departureDate.getMonth() + 1)} {departureDate.getDate()}, {departureDate.getFullYear()}</td>
            <td>{departureDate.getHours()}:{departureDate.getMinutes().toLocaleString('en-US', {
                minimumIntegerDigits: 2,
                maximumFractionDigits: 0,
                useGrouping: false
            })}</td>
            <td>{getEnglishMonth(arrivalDate.getMonth() + 1)} {arrivalDate.getDate()}, {arrivalDate.getFullYear()}</td>
            <td>{arrivalDate.getHours()}:{arrivalDate.getMinutes().toLocaleString('en-US', {
                minimumIntegerDigits: 2,
                maximumFractionDigits: 0,
                useGrouping: false
            })}</td>
            <td><Button variant="info" onClick={() => {
                navigate("/bookedFlightDetails", {
                state: {
                    flight: flight
                }})
            }}>Detail</Button></td>
            <td><Button variant="success" disabled={isConfirmationDisabled || isRequestPending} onClick={!isConfirmationDisabled ? () => confirmFlight() : null}>{flight.confirmed ? "Flight confirmed!" : flight.cancelled ? "Flight cancelled!" : "Confirm flight"}</Button> </td>
            <td><Button variant="danger" disabled={isCancellingDisabled || isRequestPending} onClick={!isCancellingDisabled ? () => cancelFlight() : null}>{flight.cancelled ? "Flight cancelled!" : flight.confirmed ? "Flight confirmed!" : "Cancel flight"}</Button></td>
        </tr>
    )
}

const UserBookedFlightsList = (props) => {
    const [bookedFlights, setBookedFlights] = useState([])
    const navigate = useNavigate()
    useEffect(() => {
        axios.get('http://localhost/api/flights/bookedFlights').then(res => {
            setBookedFlights(res.data)
        }).catch(err => {
            if (err.response.status === 403) {
                navigate("/login", {state: {alertType: 'alert-danger', alertMessage: 'Nie jesteś zalogowany'}})
            }
        })
    }, []);

    function updateFlights() {
        axios.get('http://localhost/api/flights/bookedFlights').then(res => {
            setBookedFlights(res.data)
        }).catch(err => {
            if (err.response.status === 403) {
                navigate("/login", {state: {alertType: 'alert-danger', alertMessage: 'Nie jesteś zalogowany'}})
            }
        })
    }

    return (
        <Container>
            <p className="mt-3 text-center">Your flights</p>
            <table className="table table-hover mt-3">
                <thead>
                <tr>
                    <th scope="col">From airport</th>
                    <th scope="col">To airport</th>
                    <th scope="col">Departure date</th>
                    <th scope="col">Departure time</th>
                    <th scope="col">Arrival date</th>
                    <th scope="col">Arrival time</th>
                    <th scope="col">Details</th>
                    <th scope="col">Confirm flight</th>
                    <th scope="col">Cancel flight</th>
                </tr>
                </thead>
                <tbody>
                {bookedFlights.map((flight) => <UserBookedFlightListing flight={flight} updateFlights={updateFlights}/>)}
                </tbody>
            </table>
        </Container>
    )
}

const mapStateToProps = (state) => {
    return {currentUser: state.auth.currentUser}
}

export default connect(mapStateToProps)(UserBookedFlightsList)