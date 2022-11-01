import '../SignUp.css'

const Footer = () => {
    return (
        <div className="sign-up-form-content">
            <div >
                <button type="submit" className="btn btn-primary">Submit</button>
            </div>
            <p className="forgot-password text-right mt-2">
                <a href="src/components/Account/SignUp/sections/Footer#/account/sign-in">
                    Already have an account?
                </a>
            </p>
        </div>
    )
}

export default Footer
