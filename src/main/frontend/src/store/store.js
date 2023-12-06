import { configureStore } from "@reduxjs/toolkit"
import flightReducer from '../slice/flightSlice'
import authReducer from '../slice/authSlice'

export default configureStore({
    reducer: {
        flights: flightReducer,
        auth: authReducer
    }
})