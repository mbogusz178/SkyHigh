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
                    <p className="mt-3">Znalezione loty dla {adults} dorosłych i {children} dzieci</p>
                    <table className="table table-hover mt-3">
                        <thead>
                        <tr>
                            <th scope="col">Z lotniska</th>
                            <th scope="col">Na lotnisko</th>
                            <th scope="col">Data odlotu</th>
                            <th scope="col">Godzina odlotu</th>
                            <th scope="col">Cena podróży</th>
                            <th scope="col">Rezerwuj lot</th>
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
                    <p>Nie znaleziono lotów</p>
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