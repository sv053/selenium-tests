import PropTypes from "prop-types"
import FlightCard from "../Flight/FlightCard/FlightCard"

const CatalogItem = props => {
    return (
        <FlightCard{...props}
                   buttonLeftText="Show details"
                   buttonRightText={"See tickets"}
                   actionLeft={() => window.location="/#/flights/" + props.id}
                   actionRight={() => window.location="/#/tickets?flight=" + props.id}/>
    );
}

CatalogItem.propTypes = {
    id: PropTypes.number.isRequired,
    image: PropTypes.string.isRequired,
    origin: PropTypes.string.isRequired,
    destination: PropTypes.string.isRequired,
    airline: PropTypes.string.isRequired,
    dateTime: PropTypes.string.isRequired,
};

export default CatalogItem
