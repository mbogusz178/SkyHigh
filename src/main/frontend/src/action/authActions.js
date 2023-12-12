import axios from 'axios'
import { login as requestLogin } from "../slice/authSlice";

export const login = (dispatch, email, password) => {
    const body = {
        email, password
    }

    return new Promise((resolve, reject) => axios.post("http://localhost/api/passengers/login", body).then((res) => {
        return axios.get("http://localhost/api/passengers/currentUser")
    }).then((res) => {
        dispatch(requestLogin(res.data))
        resolve(res.data)
    }).catch((err) => {
        console.log("Error code " + err.response.status)
        reject(err.response.data)
    }))
}

export const logout = (dispatch) => {
    return new Promise((resolve, reject) => axios.get("http://localhost/api/passengers/logout").then(res => {
        dispatch(requestLogin(null))
        resolve(res.data)
    }).catch(err => {
        console.log("Error code " + err.response.status)
        reject(err.response.data)
    }))
}

export const register = (dispatch, email, password, matchingPassword, firstName, lastName, city, country) => {
    const body = {
        email, password, matchingPassword, firstName, lastName, city, country
    }

    const loginBody = {
        email, password
    }

    return new Promise((resolve, reject) => axios.post("http://localhost/api/passengers/register", body).then(res => {
        return axios.post("http://localhost/api/passengers/login", loginBody)
    }).then(res => {
        return axios.get("http://localhost/api/passengers/currentUser")
    }).then(res => {
        dispatch(requestLogin(res.data))
        resolve(res.data)
    }).catch(err => {
        console.log("Error code " - err.response.status)
        reject(err.response.data)
    }))
}

export const bookFlights = (body) => {

    return new Promise((resolve, reject) => axios.post("http://localhost/api/tickets/bookTickets", body).then(res => {
        resolve(res.data)
    }).catch(err => {
        console.log("Error code " - err.response.status)
        reject(err.response)
    }))
}