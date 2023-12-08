import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

function getPolishMonth(monthNumber) {
    switch (monthNumber) {
        case 1: return "stycznia"
        case 2: return "lutego"
        case 3: return "marca"
        case 4: return "kwietnia"
        case 5: return "maja"
        case 6: return "czerwca"
        case 7: return "lipca"
        case 8: return "sierpnia"
        case 9: return "września"
        case 10: return "października"
        case 11: return "listopada"
        case 12: return "grudnia"
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
            <td>{departureDate.getDate()} {getPolishMonth(departureDate.getMonth())} {departureDate.getFullYear()}</td>
            <td>{departureDate.getHours()}:{departureDate.getMinutes().toLocaleString('pl-PL', {
                minimumIntegerDigits: 2,
                maximumFractionDigits: 0,
                useGrouping: false
            })}</td>
            <td>{flight.totalTicketPrice}</td>
            <td><Button variant="info" onClick={() => navigate("/bookFlight", {state: {flight: flight, adults: props.adults, children: props.children}})}>Rezerwuj lot</Button></td>
        </tr>
    )
}