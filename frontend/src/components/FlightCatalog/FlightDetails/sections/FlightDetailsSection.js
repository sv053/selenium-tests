import PropTypes from "prop-types"
import '../FlightDetails.css'

const FlightDetailsSection = props => {
    return (
        <div className="card-form">
            <div className="card-form-content">
                <h3 className="card-form-title">{props.title}</h3>
                <div className="form-group mt-3">
                    <label>Country:</label>
                    <div className="form-text">{props.country}</div>
                </div>
                <div className="form-group mt-3">
                    <label>Airport:</label>
                    <div className="form-text">{props.airport}</div>
                </div>
                <div className="form-group mt-3">
                    <label>Gate:</label>
                    <div className="form-text">{props.gate}</div>
                </div>
            </div>
        </div>
    )
}

FlightDetailsSection.propTypes = {
    title: PropTypes.string.isRequired,
    country: PropTypes.string.isRequired,
    airport: PropTypes.string.isRequired,
    gate: PropTypes.number.isRequired
};

export default FlightDetailsSection
