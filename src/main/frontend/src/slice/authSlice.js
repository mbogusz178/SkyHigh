import {createSlice} from "@reduxjs/toolkit";

export const authSlice = createSlice({
    name: 'auth',
    initialState: {
        currentUser: null
    },
    reducers: {
        login: (state, action) => {
            state.currentUser = action.payload
        }
    }
})

export const {login} = authSlice.actions
export default authSlice.reducer