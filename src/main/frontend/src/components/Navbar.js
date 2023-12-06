import axios from 'axios'
import {Component} from "react";
import {Link} from "react-router-dom";
import {connect, useDispatch} from "react-redux";
import { login as fetchUser} from "../slice/authSlice"

class NavbarComponent extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        axios.get("http://localhost/api/passengers/currentUser").then((res) => {
            this.props.dispatch(fetchUser(res.data))
        }).catch((err) => {
            console.log("No logged in user found")
        })
    }

    render() {
        return (
            <nav className="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
                <div className="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
                    <ul className="navbar-nav me-auto">
                        <li className="nav-item active">
                            <Link className="nav-link" to="/">Szukaj lotów</Link>
                        </li>
                    </ul>
                </div>
                <div className="mx-auto order-0">
                    <Link className="navbar-brand mx-auto" to="/">SkyHigh</Link>
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target=".dual-collapse2">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                </div>
                <div className="navbar-collapse collapse w-100 order-3 dual-collapse2">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item">
                            {(this.props.currentUser !== null) ? (
                                <div>
                                    <Link className="nav-link" to="/profile">{this.props.currentUser.firstName } {this.props.currentUser.lastName}</Link>
                                    <Link className="nav-link" to="/logout">Wyloguj się</Link>
                                </div>
                            ) : (
                                <Link className="nav-link" to="/login">Zaloguj się</Link>
                            )}
                        </li>
                    </ul>
                </div>
            </nav>
        )
    }
}

const Navbar = (props) => {
    const dispatch = useDispatch()
    return (
        <NavbarComponent currentUser={props.currentUser} dispatch={dispatch}/>
    )
}

const mapStateToProps = (state) => {
    return {currentUser: state.auth.currentUser}
}

export default connect(mapStateToProps)(Navbar)