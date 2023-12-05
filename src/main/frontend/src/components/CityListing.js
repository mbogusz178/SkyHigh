export const CityListing = (props) => {
    return (
        <div className="container">
            <h5 onClick={() => props.setCity(props.city)}>{props.city}</h5>
        </div>
    )
}