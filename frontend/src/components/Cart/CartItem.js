import PropTypes from "prop-types"
import Ticket from "../Ticket/Ticket/Ticket";

const CartItem = props => {
    return (
        <Ticket{...props}
               leftButtonText={"Show flight"}
               rightButtonText={"Delete from cart"}
               leftAction={() => window.location="#/flights/" + props.flightId}
               rightAction={() => props.onRemove(props.numberInCart)}/>
    );
}

CartItem.propTypes = {
    numberInCart: PropTypes.number.isRequired,
    id: PropTypes.number.isRequired,
    flightId: PropTypes.string.isRequired,
    airline: PropTypes.string.isRequired,
    origin: PropTypes.string.isRequired,
    destination: PropTypes.string.isRequired,
    dateTime: PropTypes.string.isRequired,
    luggageAllowed: PropTypes.bool.isRequired,
    price: PropTypes.number.isRequired,
    onRemove: PropTypes.func.isRequired
};

export default CartItem
