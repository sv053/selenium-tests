import PropTypes from "prop-types"
import Account from "../components/Account/Account"

const AccountPage = props => {
    return (
        <Account {...props}/>
    );
}

AccountPage.propTypes = {
    data: PropTypes.object.isRequired,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired
}

export default AccountPage
