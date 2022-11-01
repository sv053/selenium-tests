import PropTypes from "prop-types"

import './Error.css'

const Error = props => {
    return (
        <div className="container-fluid align-content-center">
            <div className="row">
                <div className="text-center text">{props.name}</div>
            </div>
        </div>
    );
}

Error.propTypes = {
    name: PropTypes.string.isRequired
};

export default Error
