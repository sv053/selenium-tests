import PropTypes from "prop-types"
import OrderDetails from "../components/Cart/OrderDetails/OrderDetails";

const OrderDetailsPage = props => {
    return (
        <OrderDetails {...props}/>
    );
}

OrderDetailsPage.propTypes = {
    tickets: PropTypes.array.isRequired,
    accountData: PropTypes.object,
    onSubmit: PropTypes.func.isRequired
};

export default OrderDetailsPage
