import PropTypes from "prop-types"
import Cart from "../components/Cart/Cart"

const CartPage = props => {
    return (
        <Cart {...props}/>
    );
}

CartPage.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired,
    onRemove: PropTypes.func.isRequired
};

export default CartPage
