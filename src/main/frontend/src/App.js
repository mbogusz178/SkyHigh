import './App.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {Component} from "react";
import FlightSearch from "./components/FlightSearch";
import SearchFlightList from "./components/SearchFlightList";
import Navbar from "./components/Navbar";
import {Login} from "./components/Login";
import Logout from "./components/Logout";
import Register from "./components/Register"
import {Alerts} from "./components/Alerts";
import BookFlight from "./components/BookFlight";

class App extends Component {

    render() {
        return (
            <div>
                <Router>
                    <Navbar/>
                    <Routes>
                        <Route exact path="/" element={<FlightSearch/>}/>
                        <Route exact path="/flightSearch" element={<SearchFlightList/>}/>
                        <Route exact path="/login" element={<Login/>}/>
                        <Route exact path="/logout" element={<Logout/>}/>
                        <Route exact path="/register" element={<Register/>}/>
                        <Route exact path="/bookFlight" element={<BookFlight/>}/>
                    </Routes>
                    <Alerts/>
                </Router>
            </div>
        );
    }
}

export default App;
