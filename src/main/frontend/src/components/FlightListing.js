import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

export function getEnglishMonth(monthNumber) {
    switch (monthNumber) {
        case 1: return "January"
        case 2: return "February"
        case 3: return "March"
        case 4: return "April"
        case 5: return "May"
        case 6: return "June"
        case 7: return "July"
        case 8: return "August"
        case 9: return "September"
        case 10: return "October"
        case 11: return "November"
        case 12: return "December"
        default: return monthNumber.toString()
    }
}

export const FlightListing = (props) => {
    const navigate = useNavigate()
    const flight = props.flight
    const departureDate = new Date(flight.departureDate)
    return (
        <tr className="table-primary">
            <td>{flight.source.city} ({flight.source.id})</td>
            <td>{flight.destination.city} ({flight.destination.id})</td>
            <td>{getEnglishMonth(departureDate.getMonth() + 1)} {departureDate.getDate()}, {departureDate.getFullYear()}</td>
            <td>{departureDate.getHours()}:{departureDate.getMinutes().toLocaleString('en-US', {
                minimumIntegerDigits: 2,
                maximumFractionDigits: 0,
                useGrouping: false
            })}</td>
            <td>{flight.totalTicketPrice}</td>
            <td><Button variant="info" onClick={() => navigate("/bookFlight", {state: {flight: flight, adults: props.adults, children: props.children}})}>Book flight</Button></td>
        </tr>
    )
}