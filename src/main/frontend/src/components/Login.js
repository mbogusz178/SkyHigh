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
            isLoading: false
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
                        <h1 className="display-4">Zaloguj się</h1>
                        <input id="emailInput" className={(this.state.errors.email !== null) ? "form-control form-control-lg mt-3" : "form-control form-control-lg mt-3 is-invalid"} placeholder="Adres email" type="text" value={this.props.email} onChange={this.onEmailChange} />
                        {(this.state.errors.email !== null) ? (
                            <small id="emailHelp" className="text-danger mt-1">{this.state.errors.email}</small>
                        ) : null}
                        <input id="passwordInput" className={(this.state.errors.password !== null) ? "form-control form-control-lg mt-3" : "form-control form-control-lg mt-3 is-invalid"} placeholder="Hasło" type="password" value={this.props.password} onChange={this.onPasswordChange} />
                        {(this.state.errors.password !== null) ? (
                            <small id="passwordHelp" className="text-danger mt-1">{this.state.errors.password}</small>
                        ) : null}
                        {(this.state.errors.message !== null) ? (
                            <div>
                                <small id="generalHelp" className="text-danger mt-1">{this.state.errors.message}</small>
                            </div>
                        ) : null}
                        <Button className="mt-3" variant="primary" disabled={this.state.isLoading} onClick={!this.state.isLoading ? (() => this.requestLogin(this.props.dispatch, this.props.navigate)) : null}>Zaloguj się</Button>
                        <p className="mt-3">Nie masz konta? <Link to="/register">Zarejestruj się</Link></p>
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