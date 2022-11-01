import PropTypes from "prop-types"
import '../Account.css'

const PersonalSection = props => {
    return (
        <div className="auth-form">
            <div className="auth-form-content">
                <h3 className="card-form-title">Personal data</h3>
                <div className="form-group mt-3">
                    <label>Name:</label>
                    <div className="form-text">{props.name}</div>
                </div>
                <div className="form-group mt-3">
                    <label>Nationality:</label>
                    <div className="form-text">{props.nationality}</div>
                </div>
                <div className="form-group mt-3">
                    <label>Passport number:</label>
                    <div className="form-text">{props.passportNumber}</div>
                </div>
                <div className="form-group mt-3">
                    <label>Age:</label>
                    <div className="form-text">{props.age}</div>
                </div>
            </div>
        </div>
    )
}

PersonalSection.propTypes = {
    name: PropTypes.string.isRequired,
    nationality: PropTypes.string.isRequired,
    passportNumber: PropTypes.string.isRequired,
    age: PropTypes.string.isRequired
};

export default PersonalSection
