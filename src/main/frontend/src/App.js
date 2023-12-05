import './App.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {Component} from "react";
import FlightSearch from "./components/FlightSearch";
import SearchFlightList from "./components/SearchFlightList";

class App extends Component {

    render() {
        return (
            <Router>
                <Routes>
                    <Route exact path="/" element={<FlightSearch/>}/>
                    <Route exact path="/flightSearch" element={<SearchFlightList/>}/>
                </Routes>
            </Router>
        );
    }
}

export default App;
