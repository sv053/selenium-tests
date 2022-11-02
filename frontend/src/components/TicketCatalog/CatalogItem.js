import {NotificationManager} from "react-notifications"
import PropTypes from "prop-types"
import Ticket from "../Ticket/Ticket/Ticket"

import 'react-notifications/lib/notifications.css'

const CatalogItem = props => {
    const orderClicked = () => {
        props.onOrderClick(props.id)
        NotificationManager.info("Added to cart");
    }

    return (
        <Ticket {...props}
                rightButtonText={"See flight"}
                leftButtonText={"Add to cart for $" + props.price}
                rightAction={() => window.location = "#/flights/" + props.flightId}
                leftAction={orderClicked}/>

    );
}

CatalogItem.propTypes = {
    id: PropTypes.number.isRequired,
    flightId: PropTypes.string.isRequired,
    airline: PropTypes.string.isRequired,
    origin: PropTypes.string.isRequired,
    destination: PropTypes.string.isRequired,
    dateTime: PropTypes.string.isRequired,
    luggageAllowed: PropTypes.bool.isRequired,
    price: PropTypes.number.isRequired,
    onOrderClick: PropTypes.func.isRequired
};

export default CatalogItem
