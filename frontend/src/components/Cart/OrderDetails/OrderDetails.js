import PropTypes from "prop-types"
import './OrderDetails.css'

const OrderDetails = props => {
    const submitPressed = (e) => {
        e.preventDefault()
        const ticketIds = props.tickets.map(ticket => ticket.id)
        props.onSubmit(props.accountData.email, ticketIds)
    }

    if (props.tickets.length === 0) {
        return empty()
    } else if (!props.accountData) {
        return signIn()
    } else {
        const price = props.tickets
            .map(item => item.price)
            .reduce((prev, curr) => prev + curr)
        return details(props.tickets, price, props.accountData, submitPressed)
    }
}

OrderDetails.propTypes = {
    tickets: PropTypes.array.isRequired,
    accountData: PropTypes.object,
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

const details = (tickets, price, data, submitPressed) => {
    return (
        <form className="order-container row m-lg-3" onSubmit={event => submitPressed(event)}>
            <div className="order-form">
                <div className="order-form-content">
                    <h3 className="card-form-title">Order details</h3>
                    <div className="form-group mt-3">
                        <label>Tickets selected: {tickets.length}</label>
                    </div>
                </div>
            </div>
            <div className="order-form">
                <div className="order-form-content">
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
            </div>
        </form>
    )
}

export default OrderDetails
