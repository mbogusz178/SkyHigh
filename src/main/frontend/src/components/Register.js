import {connect, useDispatch} from "react-redux";
import React, {Component} from "react";
import {Button} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import {register} from "../action/authActions";

class RegisterComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            matchingPassword: '',
            firstName: '',
            lastName: '',
            city: '',
            country: '',
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

    onMatchingPasswordChange = (e) => {
        this.setState({matchingPassword: e.target.value})
    }

    onFirstNameChange = (e) => {
        this.setState({firstName: e.target.value})
    }

    onLastNameChange = (e) => {
        this.setState({lastName: e.target.value})
    }

    onCityChange = (e) => {
        this.setState({city: e.target.value})
    }

    onCountryChange = (e) => {
        this.setState({country: e.target.value})
    }

    requestRegister = (dispatch, navigate) => {
        this.setState({errors: {}})
        this.setState({isLoading: true})
        register(dispatch, this.state.email, this.state.password, this.state.matchingPassword, this.state.firstName, this.state.lastName, this.state.city, this.state.country).then(res => {
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
                        <h1 className="display-4">Zarejestruj się</h1>
                        <input id="emailInput" className={(this.state.errors.email !== null) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Adres email" type="text" value={this.props.email} onChange={this.onEmailChange} />
                        {(this.state.errors.email !== null) ? (
                            <small id="emailHelp" className="text-danger mt-1">{this.state.errors.email}</small>
                        ) : null}
                        <input id="passwordInput" className={(this.state.errors.password !== null) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Hasło" type="password" value={this.props.password} onChange={this.onPasswordChange} />
                        {(this.state.errors.password !== null) ? (
                            <small id="passwordHelp" className="text-danger mt-1">{this.state.errors.password}</small>
                        ) : null}
                        <input id="matchingPasswordInput" className={(this.state.errors.matchingPassword !== null) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Powtórz hasło" type="password" value={this.props.matchingPassword} onChange={this.onMatchingPasswordChange} />
                        {(this.state.errors.matchingPassword !== null) ? (
                            <small id="matchingPasswordHelp" className="text-danger mt-1">{this.state.errors.matchingPassword}</small>
                        ) : null}
                        <input id="firstNameInput" className={(this.state.errors.firstName !== null) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Imię" type="text" value={this.props.firstName} onChange={this.onFirstNameChange} />
                        {(this.state.errors.firstName !== null) ? (
                            <small id="firstNameHelp" className="text-danger mt-1">{this.state.errors.firstName}</small>
                        ) : null}
                        <input id="lastNameInput" className={(this.state.errors.lastName !== null) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Nazwisko" type="text" value={this.props.lastName} onChange={this.onLastNameChange} />
                        {(this.state.errors.lastName !== null) ? (
                            <small id="lastNameHelp" className="text-danger mt-1">{this.state.errors.lastName}</small>
                        ) : null}
                        <input id="cityInput" className={(this.state.errors.city !== null) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Miasto" type="text" value={this.props.city} onChange={this.onCityChange} />
                        {(this.state.errors.city !== null) ? (
                            <small id="cityHelp" className="text-danger mt-1">{this.state.errors.city}</small>
                        ) : null}
                        <input id="countryInput" className={(this.state.errors.country !== null) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Country" type="text" value={this.props.country} onChange={this.onCountryChange} />
                        {(this.state.errors.country !== null) ? (
                            <small id="countryHelp" className="text-danger mt-1">{this.state.errors.country}</small>
                        ) : null}
                        {(this.state.errors.message !== null) ? (
                            <div>
                                <small id="generalHelp" className="text-danger mt-1">{this.state.errors.message}</small>
                            </div>
                        ) : null}
                        <Button className="mt-3" variant="primary" disabled={this.state.isLoading} onClick={!this.state.isLoading ? (() => this.requestRegister(this.props.dispatch, this.props.navigate)) : null}>Załóż konto</Button>
                    </div>
                </div>
            </div>
        )
    }
}

const Register = (props) => {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    return (
        <RegisterComponent currentUser={props.currentUser} dispatch={dispatch} navigate={navigate}/>
    )
}

const mapStateToProps = (state) => {
    return {currentUser: state.auth.currentUser}
}

export default connect(mapStateToProps)(Register)
