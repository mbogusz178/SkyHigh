import {connect} from "react-redux";
import {Container} from "react-bootstrap";

const UserBookedFlightsList = (props) => {
    return (
        <Container>

        </Container>
    )
}

const mapStateToProps = (state) => {
    return {currentUser: state.auth.currentUser}
}

export default connect(mapStateToProps)(UserBookedFlightsList)