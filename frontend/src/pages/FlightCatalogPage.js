import PropTypes from "prop-types"
import Catalog from "../components/FlightCatalog/Catalog";

const FlightCatalogPage = props => {
    return (
        <Catalog {...props}/>
    );
}

FlightCatalogPage.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired
};

export default FlightCatalogPage
