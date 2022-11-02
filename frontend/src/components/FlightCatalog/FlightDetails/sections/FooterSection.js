import PropTypes from "prop-types"
import '../FlightDetails.css'

const FooterSection = props => {
    return (
        <div className="card-form-content">
            <div className="form-group mt-3">
                <div className="d-grid gap-2 ">
                    <a className="btn btn-primary button"
                        href={"#tickets?flight=" + props.id}>
                        Show tickets
                    </a>
                </div>
            </div>
        </div>
    )
}

FooterSection.propTypes = {
    id: PropTypes.number.isRequired
};

export default FooterSection
