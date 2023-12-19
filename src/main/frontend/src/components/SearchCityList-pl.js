import {Component} from "react";
import {CityListing} from "./CityListing";

export class SearchCityList extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let filteredCities = this.props.allCities.filter((city) => {
            return city.toLowerCase().indexOf(this.props.enteredCity.toLowerCase()) !== -1;
        })
        if(filteredCities.length > 0) {
            return (
                <ul>
                    {filteredCities.map((city) => <li key={city}><CityListing city={city} setCity={this.props.setCity}/></li>)}
                </ul>
            )
        } else {
            return (
                <p>Nie znaleziono miasta</p>
            )
        }
    }
}