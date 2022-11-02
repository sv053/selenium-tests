import PropTypes from "prop-types"
import {useLocation } from "react-router-dom"
import TicketCatalog from "../components/TicketCatalog/TicketCatalog";

const TicketCatalogPage = props => {
    let id = new URLSearchParams(useLocation().search).get("flight")
    if (props.flightId) {
        id = props.flightId
    }
    return (
        <TicketCatalog {...props} flightId={id}/>
    );
}

TicketCatalogPage.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired,
    onSearch: PropTypes.func.isRequired,
    onOrderClick: PropTypes.func.isRequired
};

export default TicketCatalogPage
