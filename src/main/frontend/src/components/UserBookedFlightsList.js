import axios from 'axios'
import {connect} from "react-redux";
import {Button, Container} from "react-bootstrap";
import {getPolishMonth} from "./FlightListing";
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
            <td>{departureDate.getDate()} {getPolishMonth(departureDate.getMonth() + 1)} {departureDate.getFullYear()}</td>
            <td>{departureDate.getHours()}:{departureDate.getMinutes().toLocaleString('pl-PL', {
                minimumIntegerDigits: 2,
                maximumFractionDigits: 0,
                useGrouping: false
            })}</td>
            <td>{arrivalDate.getDate()} {getPolishMonth(arrivalDate.getMonth())} {arrivalDate.getFullYear()}</td>
            <td>{arrivalDate.getHours()}:{arrivalDate.getMinutes().toLocaleString('pl-PL', {
                minimumIntegerDigits: 2,
                maximumFractionDigits: 0,
                useGrouping: false
            })}</td>
            <td><Button variant="info" onClick={() => {
                console.log(flight)
                navigate("/bookedFlightDetails", {
                state: {
                    flight: flight
                }})
            }}>Szczegóły</Button></td>
            <td><Button variant="success" disabled={isConfirmationDisabled || isRequestPending} onClick={!isConfirmationDisabled ? () => confirmFlight() : null}>{flight.confirmed ? "Lot potwierdzony!" : flight.cancelled ? "Lot odwołany!" : "Potwierdź lot"}</Button> </td>
            <td><Button variant="danger" disabled={isCancellingDisabled || isRequestPending} onClick={!isCancellingDisabled ? () => cancelFlight() : null}>{flight.cancelled ? "Lot odwołany!" : flight.confirmed ? "Lot potwierdzony!" : "Odwołaj lot"}</Button></td>
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
            <p className="mt-3 text-center">Twoje loty</p>
            <table className="table table-hover mt-3">
                <thead>
                <tr>
                    <th scope="col">Z lotniska</th>
                    <th scope="col">Na lotnisko</th>
                    <th scope="col">Data odlotu</th>
                    <th scope="col">Godzina odlotu</th>
                    <th scope="col">Data przylotu</th>
                    <th scope="col">Godzina przylotu</th>
                    <th scope="col">Szczegóły</th>
                    <th scope="col">Potwierdź lot</th>
                    <th scope="col">Odwołaj lot</th>
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