import { createSlice } from "@reduxjs/toolkit";

export const flightSlice = createSlice({
    name: 'flights',
    initialState: {
        flights: []
    },
    reducers: {
        searchFlights: (state, action) => {
            state.flights = action.payload
        }
    }
})

export const { searchFlights } = flightSlice.actions

export default flightSlice.reducer