import PropTypes from "prop-types"
import SignUp from "../components/Account/SignUp/SignUp"

const SignUpPage = props => {
    return (
        <SignUp {...props}/>
    );
}

SignUpPage.propTypes = {
    onSubmit: PropTypes.func.isRequired
};

export default SignUpPage
