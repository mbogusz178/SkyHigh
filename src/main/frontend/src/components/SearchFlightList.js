import {Component} from "react";
import {connect, useDispatch, useSelector} from "react-redux";
import {FlightListing} from "./FlightListing";

class SearchFlightListComponent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        console.log(this.props.flights)
        if(this.props.flights.length > 0) {
            return (
                <div className="container">
                    <table className="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">Z lotniska</th>
                            <th scope="col">Na lotnisko</th>
                            <th scope="col">Data odlotu</th>
                            <th scope="col">Godzina odlotu</th>
                            <th scope="col">Cena podróży</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.props.flights.map((flight) => <FlightListing flight={flight}/>)}
                        </tbody>
                    </table>
                </div>
            )
        } else {
            return (
                <p>Nie znaleziono lotów</p>
            )
        }
    }
}

const SearchFlightList = (props) => {
    const dispatch = useDispatch()
    return (
        <SearchFlightListComponent flights={props.flights} dispatch={dispatch}/>
    )
}

const mapStateToProps = (state) => {
    return {flights: state.flights.flights}
}

export default connect(mapStateToProps)(SearchFlightList)