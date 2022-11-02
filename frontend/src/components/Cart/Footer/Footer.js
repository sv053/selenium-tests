import PropTypes from "prop-types"
import './Footer.css'

const Footer = (props) => {
    return (
        <div className="card-container row gx-5">
            <div className="footer-form">
                <div className="footer-form-content">
                    <h3 className="footer-form-form-title">{"Total: $" + props.total}</h3>
                    <div className="form-group mt-3">
                        <div className="d-grid gap-2 align-top">
                            <a className="btn btn-primary button" href="#/cart/order">
                                Proceed to order details
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

Footer.propTypes = {
    total: PropTypes.number.isRequired
}

export default Footer
