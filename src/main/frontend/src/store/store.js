import { configureStore } from "@reduxjs/toolkit"
import flightReducer from '../slice/flightSlice'

export default configureStore({
    reducer: {
        flights: flightReducer
    }
})