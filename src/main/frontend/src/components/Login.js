import React, {Component} from "react";
import {Link, useNavigate} from "react-router-dom";
import {Button} from "react-bootstrap";
import {useDispatch} from "react-redux";
import {login} from "../action/authActions";

class LoginComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            errors: {},
            isLoading: false,
            logoutFailedToast: false,
        }
    }

    onEmailChange = (e) => {
        this.setState({email: e.target.value})
    }

    onPasswordChange = (e) => {
        this.setState({password: e.target.value})
    }

    requestLogin = (dispatch, navigate) => {
        this.setState({errors: {}})
        this.setState({isLoading: true})
        login(dispatch, this.state.email, this.state.password).then(res => {
            navigate("/")
        }).catch(err => {
            this.setState({errors: err})
        }).finally(() => {
            this.setState({isLoading: false})
        })
    }

    render() {
        return (
            <div>
                <div className="jumbotron mt-1">
                    <div className="container text-center mt-3">
                        <h1 className="display-4">Log in</h1>
                        <input id="emailInput" className={('email' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Email" type="text" value={this.props.email} onChange={this.onEmailChange} />
                        {('email' in this.state.errors) ? (
                            <small id="emailHelp" className="text-danger mt-1">{this.state.errors.email}</small>
                        ) : null}
                        <input id="passwordInput" className={('password' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Password" type="password" value={this.props.password} onChange={this.onPasswordChange} />
                        {('password' in this.state.errors) ? (
                            <small id="passwordHelp" className="text-danger mt-1">{this.state.errors.password}</small>
                        ) : null}
                        {('message' in this.state.errors) ? (
                            <div>
                                <small id="generalHelp" className="text-danger mt-1">{this.state.errors.message}</small>
                            </div>
                        ) : null}
                        <Button className="mt-3" variant="primary" disabled={this.state.isLoading} onClick={!this.state.isLoading ? (() => this.requestLogin(this.props.dispatch, this.props.navigate)) : null}>Log in</Button>
                        <p className="mt-3">Don't have an account? <Link to="/register">Register</Link></p>
                    </div>
                </div>
            </div>
        )
    }
}

export const Login = () => {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    return (
        <LoginComponent dispatch={dispatch} navigate={navigate}/>
    )
}
