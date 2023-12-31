import {Component} from "react";
import {connect, useDispatch, useSelector} from "react-redux";
import {FlightListing} from "./FlightListing";
import {useLocation} from "react-router-dom";

class SearchFlightListComponent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let adults = 1
        let children = 0
        if('state' in this.props.location && this.props.location.state !== null) {
            if('adults' in this.props.location.state) {
                adults = this.props.location.state.adults
            }
            if('children' in this.props.location.state) {
                children = this.props.location.state.children
            }
        }
        if(this.props.flights.length > 0) {
            return (
                <div className="container">
                    <p className="mt-3">Flights found for {adults} adults and {children} children</p>
                    <table className="table table-hover mt-3">
                        <thead>
                        <tr>
                            <th scope="col">From airport</th>
                            <th scope="col">To airport</th>
                            <th scope="col">Departure date</th>
                            <th scope="col">Departure time</th>
                            <th scope="col">Flight price</th>
                            <th scope="col">Book flight</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.props.flights.map((flight) => <FlightListing flight={flight} adults={adults} children={children}/>)}
                        </tbody>
                    </table>
                </div>
            )
        } else {
            return (
                <div className="container mt-3">
                    <p>No flights found</p>
                </div>
            )
        }
    }
}

const SearchFlightList = (props) => {
    const dispatch = useDispatch()
    const location = useLocation()
    return (
        <SearchFlightListComponent flights={props.flights} dispatch={dispatch} location={location}/>
    )
}

const mapStateToProps = (state) => {
    return {flights: state.flights.flights}
}

export default connect(mapStateToProps)(SearchFlightList)