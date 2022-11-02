import PropTypes from "prop-types"
import {Button} from "@mui/material"
import toPrettyDate from "../../../utils"

import './TicketCard.css'

const Ticket = props => {
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
                                onClick={props.leftAction}>
                            {props.leftButtonText}
                        </Button>
                        <Button variant="primary info button"
                                onClick={props.rightAction}>
                            {props.rightButtonText}
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
}

Ticket.propTypes = {
    id: PropTypes.number.isRequired,
    airline: PropTypes.string.isRequired,
    origin: PropTypes.string.isRequired,
    destination: PropTypes.string.isRequired,
    dateTime: PropTypes.string.isRequired,
    luggageAllowed: PropTypes.bool.isRequired,
    rightButtonText: PropTypes.string.isRequired,
    leftButtonText: PropTypes.string.isRequired,
    rightAction: PropTypes.func.isRequired,
    leftAction: PropTypes.func.isRequired
};

export default Ticket
