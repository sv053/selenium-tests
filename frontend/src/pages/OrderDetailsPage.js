import PropTypes from "prop-types"
import OrderDetails from "../components/Cart/OrderDetails/OrderDetails";

const OrderDetailsPage = props => {
    return (
        <OrderDetails {...props}/>
    );
}

OrderDetailsPage.propTypes = {
    accountData: PropTypes.object,
    price: PropTypes.number,
    onSubmit: PropTypes.func.isRequired
};

export default OrderDetailsPage
