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
    const flight = props.flight
    const departureDate = new Date(flight.departureDate)
    return (
        <tr className="table-primary">
            <td>{flight.source.city} ({flight.source.id})</td>
            <td>{flight.destination.city} ({flight.destination.id})</td>
            <td>{departureDate.getDay()} {getPolishMonth(departureDate.getMonth())} {departureDate.getFullYear()}</td>
            <td>{departureDate.getHours()}:{departureDate.getMinutes()}</td>
            <td>{flight.totalTicketPrice}</td>
        </tr>
    )
}