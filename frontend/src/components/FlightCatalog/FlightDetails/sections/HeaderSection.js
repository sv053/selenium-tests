import PropTypes from "prop-types"
import ImageViewer from "./ImageViewer"
import toPrettyDate from "../../../../utils";

import '../FlightDetails.css'

const HeaderSection = props => {
    const date = toPrettyDate(props.dateTime)
    return (
        <div className="card-form">
            <div className="card-form-content">
                <h3 className="card-form-title">{props.origin + " -> " + props.destination}</h3>
                <div className="form-group mt-3">
                    <label>Airline:</label>
                    <div className="form-text">{props.airline}</div>
                </div>
                <div className="form-group mt-3">
                    <label>Date and time:</label>
                    <div className="form-text">{date}</div>
                </div>
                <div className="form-group mt-3">
                    <ImageViewer images={[props.image]} name={props.airline}/>
                </div>
            </div>
        </div>
    )
}

HeaderSection.propTypes = {
    airline: PropTypes.string.isRequired,
    origin: PropTypes.string.isRequired,
    destination: PropTypes.string.isRequired,
    dateTime: PropTypes.string.isRequired,
    image: PropTypes.string.isRequired,
};

export default HeaderSection
