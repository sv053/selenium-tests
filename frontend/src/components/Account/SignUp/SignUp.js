import PropTypes from "prop-types"
import SignUpSection from "./sections/SignUpSection"
import PersonalSection from "./sections/PersonalSection"
import Footer from "./sections/Footer"

import './SignUp.css'

const SignUp = props => {
    const submitPressed = (e) => {
        e.preventDefault()
        const formData = {
            email: e.target.elements.email.value,
            password: e.target.elements.password.value,
            repeatPassword: e.target.elements.repeatPassword.value,
            name: e.target.elements.name.value,
            nationality: e.target.elements.nationality.value,
            passportNumber: e.target.elements.passportNumber.value,
            age: e.target.elements.age.value
        }
        props.onSubmit(formData)
    }

    return (
        <form onSubmit={event => submitPressed(event)}>
            <div className="sign-up-container row m-lg-3">
                <SignUpSection/>
                <PersonalSection/>
                <Footer/>
            </div>
        </form>
    )
}

SignUp.propTypes = {
    onSubmit: PropTypes.func.isRequired
};

export default SignUp
