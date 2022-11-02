import PropTypes from "prop-types"
import {useLocation } from "react-router-dom"
import TicketList from "../components/Ticket/TicketList/TicketList"

const TicketCatalogPage = props => {
    const id = new URLSearchParams(useLocation().search).get("flight")
    return (
        <TicketList {...props} flightId={id}/>
    );
}

TicketCatalogPage.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired,
    onOrderClick: PropTypes.func.isRequired
};

export default TicketCatalogPage
