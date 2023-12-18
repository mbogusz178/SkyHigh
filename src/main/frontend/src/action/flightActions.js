import axios from 'axios'
import {searchFlights} from "../slice/flightSlice";

export const getFlights = (dispatch, source, destination, departureAfter, departureBefore, arrivalAfter, arrivalBefore, adultCount, childCount, flightTicketMinPrice, flightTicketMaxPrice) => {
    const params = {
        source, destination, departureAfter, departureBefore, arrivalAfter, arrivalBefore, adultCount, childCount, flightTicketMinPrice, flightTicketMaxPrice
    }

    return new Promise((resolve, reject) => axios.get("http://localhost/api/flights/search", { params }).then((res) => {
        dispatch(searchFlights(res.data))
        resolve()
    }).catch((err) => {
        console.log("Error code " + err.response.status)
        reject()
    }))
}
export const bookFlights = (body) => {

    return new Promise((resolve, reject) => axios.post("http://localhost/api/tickets/bookTickets", body).then(res => {
        resolve(res.data)
    }).catch(err => {
        console.log("Error code " + err.response.status)
        reject(err.response)
    }))
}

export const editFlight = (body) => {
    return new Promise((resolve, reject) => axios.put("http://localhost/api/tickets/editTicket", body).then(res => {
        resolve(res.data)
    }).catch(err => {
        console.log("Error code " + err.response.status)
        reject(err.response)
    }))
}