import PropTypes from "prop-types"
import {NotificationManager} from "react-notifications"
import {Button} from "@mui/material"
import toPrettyDate from "../../../utils"

import './TicketCard.css'
import 'react-notifications/lib/notifications.css';

const Ticket = props => {
    const orderClicked = () => {
        props.onOrderClick(props.id)
        NotificationManager.info("Added to cart");
    }

    const date = toPrettyDate(props.dateTime)
    return (
        <div className="card-form">
            <div className="card-form-content">
                <div id="ticket" className="ticket">
                    <div className="part-2">
                        <h5 className="title">{props.origin + " -> " + props.destination}</h5>
                        <h4>{props.airline + ", " + date}</h4>
                        <h6>Luggage allowed:  {(props.luggageAllowed)? "yes" : "no"}</h6>
                    </div>
                    <div className="btn-group group">
                        <Button variant="outline-info info button"
                                onClick={() => window.location = "#/flights/" + props.flightId}>
                            See flight
                        </Button>
                        <Button variant="primary info button"
                                onClick={orderClicked}>
                            {"Add to cart for $" + props.price}
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
}

Ticket.propTypes = {
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

export default Ticket
