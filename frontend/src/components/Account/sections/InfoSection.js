import PropTypes from "prop-types"
import '../Account.css'

const InfoSection = props => {
    return (
        <div className="auth-form">
            <div className="auth-form-content">
                <h3 className="card-form-title">Login info</h3>
                <div className="form-group mt-3">
                    <label>Email:</label>
                    <div className="form-text">{props.email}</div>
                </div>
            </div>
        </div>
    )
}

InfoSection.propTypes = {
    email: PropTypes.string.isRequired
};

export default InfoSection
