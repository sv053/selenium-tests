import PropTypes from "prop-types"
import SignIn from "../components/Account/SignIn/SignIn"

const SignInPage = props => {
    return (
        <SignIn {...props}/>
    );
}

SignInPage.propTypes = {
    onSubmit: PropTypes.func.isRequired
};

export default SignInPage
