import PropTypes from "prop-types"
import TicketList from "../Ticket/TicketList/TicketList"
import CartItem from "./CartItem"
import Footer from "./Footer/Footer"

const Cart = props => {
    const tickets = props.items.map(item => {
        return <CartItem numberInCart={item.numberInCart}
                         id={item.id}
                         flightId={item.flight.id}
                         airline={item.flight.airline.name}
                         origin={item.flight.from.country + ", " + item.flight.from.airport}
                         destination={item.flight.to.country + ", " + item.flight.to.airport}
                         dateTime={item.flight.dateTime}
                         luggageAllowed={item.luggageAllowed}
                         price={item.price}
                         onRemove={props.onRemove}/>
    })

    if (tickets.length === 0) {
        return emptyCart()
    } else {
        const total = props.items
            .map(item => item.price)
            .reduce((prev, curr) => prev + curr)
        return cart(tickets, props.loading, total)
    }
}

Cart.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    onRemove: PropTypes.func.isRequired
}

const emptyCart = () => {
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

const cart = (tickets, isLoading, total) => {
    return (
        <div>
            <TicketList items={tickets} loading={isLoading}/>
            <Footer total={total}/>
        </div>
    )
}

export default Cart