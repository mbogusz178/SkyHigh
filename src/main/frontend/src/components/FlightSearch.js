import axios from 'axios'
import React, {Component} from "react";
import {useSelector, useDispatch, connect} from 'react-redux'
import {SearchCityList} from "./SearchCityList";
import {Button, Form} from "react-bootstrap";
import setInputValue from "../util/setInputValue";
import {getFlights} from "../action/flightActions";
import {useLocation, useNavigate} from "react-router-dom";
import {Toast} from "bootstrap";

class FlightSearchComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            cities: [],
            source: '',
            destination: '',
            departureAfter: null,
            departureBefore: null,
            adults: 1,
            children: 0,
            minFlightPrice: 0,
            maxFlightPrice: 1000,
        }
    }

    componentDidMount() {
        axios.get("http://localhost/api/cities").then((res) => {
            this.setState({cities: res.data})
        })
        const adultsInput = document.getElementById("adultsInput")
        setInputValue(adultsInput, this.props.adults)
        const childrenInput = document.getElementById("childrenInput")
        setInputValue(childrenInput, this.props.children)
        const minFlightPriceInput = document.getElementById("minFlightPriceInput")
        setInputValue(minFlightPriceInput, this.props.minFlightPrice)
        const maxFlightPriceInput = document.getElementById("maxFlightPriceInput")
        setInputValue(maxFlightPriceInput, this.props.maxFlightPrice)
    }

    onSourceChange = (e) => {
        this.setState({source: e.target.value})
    }

    onDestChange = (e) => {
        this.setState({destination: e.target.value})
    }

    onAdultsChange = (e) => {
        this.setState({adults: e.target.value})
    }

    onChildrenChange = (e) => {
        this.setState({children: e.target.value})
    }

    onMinFlightPriceChange = (e) => {
        this.setState({minFlightPrice: e.target.value})
    }

    onMaxFlightPriceChange = (e) => {
        this.setState({maxFlightPrice: e.target.value})
    }

    onDepartureAfterChange = (e) => {
        this.setState({departureAfter: new Date(e.target.value)})
    }

    onDepartureBeforeChange = (e) => {
        this.setState({departureBefore: new Date(e.target.value)})
    }

    setSource = (city) => {
        this.setState({source: city})
        const sourceInput = document.getElementById("sourceInput")
        setInputValue(sourceInput, city)
    }

    setDestination = (city) => {
        this.setState({destination: city})
        const destInput = document.getElementById("destInput")
        setInputValue(destInput, city)
    }

    requestSearchFlights = (navigate, dispatch) => {
        const departureAfterString = this.state.departureAfter.toISOString().split('.')[0]+"Z"
        const departureBeforeString = this.state.departureBefore.toISOString().split('.')[0]+"Z"
        getFlights(dispatch, this.state.source, this.state.destination, departureAfterString, departureBeforeString, null, null, this.state.adults, this.state.children, this.state.minFlightPrice, this.state.maxFlightPrice).then(() => {
            navigate("/flightSearch", {state: {adults: this.state.adults, children: this.state.children}})
        })
    }

    render() {
        const flights = this.props.flights
        const dispatch = this.props.dispatch
        const navigate = this.props.navigate
        const isLoading = false

        return (
                <div>
                    <div className="jumbotron mt-1">
                        <div className="container text-center mt-3">
                            <h1 className="display-4">Wyszukaj loty</h1>
                            <p className="lead">Gdzie się wybierasz? Wypełnij pola i odbądź wymarzoną podróż!</p>
                            <input id="sourceInput" className="form-control form-control-lg mt-3" placeholder="Miasto początkowe" type="text" value={this.props.source} onChange={this.onSourceChange} />
                            <input id="destInput" className="form-control form-control-lg mt-3" placeholder="Miasto docelowe" type="text" value={this.props.destination} onChange={this.onDestChange} />
                        </div>
                    </div>
                    <div className="container mt-3">
                        <div className="row mt-3">
                            <div className="col-md">
                                <h5>Dorośli:</h5>
                            </div>
                            <input id="adultsInput" className="form-control form-control-lg col-md" type="number" value={this.props.adults} onChange={this.onAdultsChange} min="1" />
                            <div className="col-md"/>
                            <div className="col-md">
                                <h5>Dzieci:</h5>
                            </div>
                            <input id="childrenInput" className="form-control form-control-lg col-md" type="number" value={this.props.children} onChange={this.onChildrenChange} min="0" />
                        </div>
                        <div className="row mt-3">
                            <div className="col-md">
                                <h5>Cena lotu:</h5>
                            </div>
                            <div className="col-md">
                                <h5>od</h5>
                            </div>
                            <div className="col-md">
                                <input id="minFlightPriceInput" className="form-control form-control-lg col-md" type="number" value={this.props.minFlightPrice} onChange={this.onMinFlightPriceChange} min="0"/>
                            </div>
                            <div className="col-md">
                                <h5>do</h5>
                            </div>
                            <div className="col-md">
                                <input id="maxFlightPriceInput" className="form-control form-control-lg col-md" type="number" value={this.props.maxFlightPrice} onChange={this.onMaxFlightPriceChange} min="0"/>
                            </div>
                        </div>
                        <div className="row mt-3">
                            <div className="col-md">
                                <h5>Najwcześniejszy odlot:</h5>
                            </div>
                            <div className="col-md">
                                <h5>Najpóźniejszy odlot:</h5>
                            </div>
                        </div>
                        <div className="row mt-3">
                            <Form.Control type="date" value={null} onChange={this.onDepartureAfterChange}/>
                            <Form.Control type="date" value={null} onChange={this.onDepartureBeforeChange}/>
                        </div>
                        <div className="row mt-3">
                            <div className="listHeading mt-4 col-md">
                                <h1>Miasta:</h1>
                            </div>
                            <div className="listHeading mt-4 col-md">
                                <h1>Miasta:</h1>
                            </div>
                        </div>
                        <div className="row mt-3">
                            <div className="col-md">
                                <SearchCityList allCities={this.state.cities} enteredCity={this.state.source} setCity={this.setSource}/>
                            </div>
                            <div className="col-md">
                                <SearchCityList allCities={this.state.cities} enteredCity={this.state.destination} setCity={this.setDestination}/>
                            </div>
                        </div>
                        <Button variant="primary" disabled={isLoading} onClick={!isLoading ? (() => this.requestSearchFlights(navigate, dispatch)) : null}>Szukaj</Button>
                    </div>
                </div>
    )}
}

const FlightSearch = (props) => {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const location = useLocation()
    return (
        <FlightSearchComponent flights={props.flights} dispatch={dispatch} navigate={navigate} location={location}/>
    )
}

const mapStateToProps = (state) => {
    return {flights: state.flights.flights}
}

export default connect(mapStateToProps)(FlightSearch)