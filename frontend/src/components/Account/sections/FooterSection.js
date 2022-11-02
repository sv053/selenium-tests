import PropTypes from "prop-types";
import '../Account.css'

const FooterSection = props => {
    return (
        <div className="account-form-content">
            <p className="forgot-password text-right mt-2">
                <button className="btn btn-primary button"
                        onClick={props.onSignOut}>
                   Sign out
                </button>
            </p>
        </div>
    )
}

FooterSection.propTypes = {
    onSignOut: PropTypes.func.isRequired
};


export default FooterSection
