import PropTypes from "prop-types"
import {Button} from "@mui/material"
import toPrettyDate from "../../../utils";

import './FlightCard.css'

const FlightCard = props => {
    const date = toPrettyDate(props.dateTime)
    const image = "data:image/png;base64, " + props.image.replace(/(\r\n|\n|\r)/gm, "")
    return (
        <div className="card-form">
            <div className="card-form-content">
                <div id="flight" className="flight">
                    <div className="part-1"
                         style={{ background: "url(\"" +  image + "\")  no-repeat center" }}>
                    </div>
                    <div className="part-2">
                        <h4 className="title">{props.origin + " -> " + props.destination}</h4>
                        <h4>{props.airline + ", " + date}</h4>
                    </div>
                    <div className="btn-group group">
                        <Button variant="outline-info info button" onClick={props.actionLeft}>
                            {props.buttonLeftText}
                        </Button>
                        <Button variant="primary info button" onClick={props.actionRight}>
                            {props.buttonRightText}
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
}

FlightCard.propTypes = {
    id: PropTypes.number.isRequired,
    image: PropTypes.string.isRequired,
    origin: PropTypes.string.isRequired,
    destination: PropTypes.string.isRequired,
    airline: PropTypes.string.isRequired,
    dateTime: PropTypes.string.isRequired,
    buttonLeftText: PropTypes.string.isRequired,
    buttonRightText: PropTypes.string.isRequired,
    actionRight: PropTypes.func.isRequired,
    actionLeft: PropTypes.func.isRequired
};

export default FlightCard
