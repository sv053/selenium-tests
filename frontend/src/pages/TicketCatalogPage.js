import PropTypes from "prop-types"
import {useLocation } from "react-router-dom"
import TicketCatalog from "../components/TicketCatalog/TicketCatalog";

const TicketCatalogPage = props => {
    const id = new URLSearchParams(useLocation().search).get("flight")
    return (
        <TicketCatalog {...props} flightId={id}/>
    );
}

TicketCatalogPage.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired,
    onOrderClick: PropTypes.func.isRequired
};

export default TicketCatalogPage
