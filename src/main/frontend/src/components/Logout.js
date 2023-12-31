import {connect, useDispatch} from "react-redux";
import {logout} from "../action/authActions";
import {Navigate} from "react-router-dom";

const Logout = (props) => {
    const dispatch = useDispatch()

    let success = true
    if(props.currentUser === null) {
        success = false
    } else {
        logout(dispatch).then(res => {
            success = true
        }).catch(err => {
            success = false
        })
    }

    if(success) {
        return <Navigate to='/' state={{alertType: 'alert-success', alertMessage: 'Successfully logged out'}}/>
    }

    return <Navigate to="/login" state={{alertType: 'alert-info', alertMessage: 'You are not logged in'}}/>
}

const mapStateToProps = (state) => {
    return {currentUser: state.auth.currentUser}
}

export default connect(mapStateToProps)(Logout)