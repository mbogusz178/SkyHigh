import {connect, useDispatch} from "react-redux";
import {Navigate, useNavigate} from "react-router-dom"
import {logout} from "../action/authActions";
import {useEffect} from "react";

const Logout = (props) => {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    useEffect(() => {
        setTimeout(() => navigate("/login"), 2000)
    }, []);

    let success = true
    if(props.currentUser === null) {
        success = false
    } else {
        let result = ""
        logout(dispatch).then(res => {
            success = true
        }).catch(err => {
            success = false
        })
    }

    if(success) {
        return <h5>Wylogowano pomyślnie</h5>
    }

    return <h5>Nie jesteś zalogowany</h5>
}

const mapStateToProps = (state) => {
    return {currentUser: state.auth.currentUser}
}

export default connect(mapStateToProps)(Logout)