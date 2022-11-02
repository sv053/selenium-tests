import {useEffect} from "react";
import PropTypes from "prop-types";
import TicketList from "../Ticket/TicketList/TicketList";
import CatalogItem from "./CatalogItem";

const TicketCatalog = props => {
    useEffect(() => {
        props.onLoad(props.flightId)
    }, [])

    const items = props.items.map(item => {
        return <CatalogItem id={item.id}
                            flightId={props.flightId}
                            airline={item.flight.airline.name}
                            origin={item.flight.from.country + ", " + item.flight.from.airport}
                            destination={item.flight.to.country + ", " + item.flight.to.airport}
                            dateTime={item.flight.dateTime}
                            luggageAllowed={item.luggageAllowed}
                            price={item.price}
                            onOrderClick={props.onOrderClick}/>
    })

    return (
        <TicketList items={items}
                    loading={props.loading}/>
    )
}

TicketCatalog.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    flightId: PropTypes.string.isRequired,
    onLoad: PropTypes.func.isRequired,
    onOrderClick: PropTypes.func.isRequired
}

export default TicketCatalog