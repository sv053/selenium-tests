import PropTypes from "prop-types"

import './OrderDetails.css'

const OrderDetails = props => {
    const submitPressed = (e) => {
        e.preventDefault()
        props.onSubmit(props.data.email)
    }

    if (!props.price) {
        return empty()
    } else if (!props.accountData) {
        return signIn()
    } else {
        return details(props.accountData, props.price, submitPressed)
    }
}

OrderDetails.propTypes = {
    accountData: PropTypes.object,
    price: PropTypes.number,
    onSubmit: PropTypes.func.isRequired
}

const empty = () => {
    return (
        <div className="container-fluid align-content-center">
            <div className="row">
                <div className="text-center text">Cart is empty</div>
            </div>
            <div className="text-center button-layout">
                <a className="btn btn-primary button" href="/">Back to flights</a>
            </div>
        </div>
    );
}

const signIn = () => {
    return (
        <div className="container-fluid align-content-center">
            <div className="row">
                <div className="text-center text">Please, sign in to continue</div>
            </div>
            <div className="text-center button-layout">
                <a className="btn btn-primary button" href="#/account/sign-in">Sign in</a>
            </div>
        </div>
    );
}

const details = (data, price, submitPressed) => {
    return (
        <div className="order-container">
            <form className="order-form" onSubmit={event => submitPressed(event)}>
                <div className="order-form-content">
                    <h3 className="card-form-title">Order details</h3>
                    <div className="form-group mt-3">
                        <label>Email:</label>
                        <div className="form-text">{data.email}</div>
                    </div>
                    <div className="form-group mt-3">
                        <label>Name:</label>
                        <div className="form-text">{data.name}</div>
                    </div>
                    <div className="form-group mt-3">
                        <label>Passport number:</label>
                        <div className="form-text">{data.passportNumber}</div>
                    </div>

                    <div className="d-grid gap-2 mt-3">
                        <button type="submit" className="btn btn-primary">
                            { "Submit $" + price + " order" }
                        </button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default OrderDetails
