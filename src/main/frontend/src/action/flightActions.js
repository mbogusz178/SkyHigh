import axios from 'axios'
import {FLIGHT_SEARCH_SUCCESSFUL, ERROR} from "./responseTypes";
import { searchFlights } from "../slice/flightSlice";

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