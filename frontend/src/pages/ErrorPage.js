import {useLocation} from "react-router-dom"
import Error from "../components/Error/Error"
import PropTypes from "prop-types"

const ErrorPage = (props) => {
    let message = new URLSearchParams(useLocation().search).get("message")
    if (props.message) {
        message = props.message
    }

    return (
        <Error name={message}/>
    );
}

ErrorPage.propTypes = {
    message: PropTypes.string,
};

export default ErrorPage
