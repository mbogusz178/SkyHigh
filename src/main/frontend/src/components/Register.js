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
            console.log(err)
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
                        <input id="emailInput" className={('email' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Adres email" type="text" value={this.props.email} onChange={this.onEmailChange} />
                        {('email' in this.state.errors) ? (
                            <small id="emailHelp" className="text-danger mt-1">{this.state.errors.email}</small>
                        ) : null}
                        <input id="passwordInput" className={('password' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Hasło" type="password" value={this.props.password} onChange={this.onPasswordChange} />
                        {('password' in this.state.errors) ? (
                            <small id="passwordHelp" className="text-danger mt-1">{this.state.errors.password}</small>
                        ) : null}
                        <input id="matchingPasswordInput" className={('matchingPassword' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Powtórz hasło" type="password" value={this.props.matchingPassword} onChange={this.onMatchingPasswordChange} />
                        {('matchingPassword' in this.state.errors) ? (
                            <small id="matchingPasswordHelp" className="text-danger mt-1">{this.state.errors.matchingPassword}</small>
                        ) : null}
                        <input id="firstNameInput" className={('firstName' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Imię" type="text" value={this.props.firstName} onChange={this.onFirstNameChange} />
                        {('firstName' in this.state.errors) ? (
                            <small id="firstNameHelp" className="text-danger mt-1">{this.state.errors.firstName}</small>
                        ) : null}
                        <input id="lastNameInput" className={('lastName' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Nazwisko" type="text" value={this.props.lastName} onChange={this.onLastNameChange} />
                        {('lastName' in this.state.errors) ? (
                            <small id="lastNameHelp" className="text-danger mt-1">{this.state.errors.lastName}</small>
                        ) : null}
                        <input id="cityInput" className={('city' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Miasto" type="text" value={this.props.city} onChange={this.onCityChange} />
                        {('city' in this.state.errors) ? (
                            <small id="cityHelp" className="text-danger mt-1">{this.state.errors.city}</small>
                        ) : null}
                        <input id="countryInput" className={('country' in this.state.errors) ? "form-control form-control-lg mt-3 is-invalid" : "form-control form-control-lg mt-3"} placeholder="Country" type="text" value={this.props.country} onChange={this.onCountryChange} />
                        {('country' in this.state.errors) ? (
                            <small id="countryHelp" className="text-danger mt-1">{this.state.errors.country}</small>
                        ) : null}
                        {('message' in this.state.errors) ? (
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
