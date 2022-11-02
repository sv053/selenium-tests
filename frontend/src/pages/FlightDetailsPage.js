import PropTypes from "prop-types"
import {useParams} from "react-router-dom"
import FlightDetails from "../components/FlightCatalog/FlightDetails/FlightDetails";

const FlightDetailsPage = props => {
    const { id } = useParams()
    return (
        <FlightDetails id={id} {...props}/>
    );
}

FlightDetailsPage.propTypes = {
    details: PropTypes.object,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired,
};

export default FlightDetailsPage
