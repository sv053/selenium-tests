import PropTypes from "prop-types"
import './SignIn.css'

const SignIn = props => {
    const submitPressed = (e) => {
        e.preventDefault()
        props.onSubmit(
            e.target.elements.email.value,
            e.target.elements.password.value)
    }

    return (
        <div className="auth-form-container">
            <form className="auth-form" onSubmit={event => submitPressed(event)}>
                <div className="auth-form-content">
                    <h3 className="card-form-title">Sign In</h3>
                    <div className="form-group mt-3">
                        <label>Email address</label>
                        <input type="email"
                               className="form-control mt-1"
                               id="email"
                               required="true"
                               placeholder="Enter email"/>
                    </div>
                    <div className="form-group mt-3">
                        <label>Password</label>
                        <input type="password"
                               className="form-control mt-1"
                               id="password"
                               required="true"
                               placeholder="Enter password"/>
                    </div>
                    <div className="d-grid gap-2 mt-3">
                        <button type="submit" className="btn btn-primary">
                            Submit
                        </button>
                    </div>
                    <p className="forgot-password text-right mt-2">
                        <a href="#/account/sign-up">
                            Don't have an account?
                        </a>
                    </p>
                    <p className="forgot-password text-right mt-2">
                        <a href="#/account/reset-password">
                            Forgot password?
                        </a>
                    </p>
                </div>
            </form>
        </div>
    )
}

SignIn.propTypes = {
    onSubmit: PropTypes.func.isRequired
};

export default SignIn
