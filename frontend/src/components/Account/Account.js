import {useEffect} from "react"
import PropTypes from "prop-types"
import ClipLoader from "react-spinners/ClipLoader"
import InfoSection from "./sections/InfoSection"
import PersonalSection from "./sections/PersonalSection"

import './Account.css'

const Account = props => {
    useEffect(() => {
        props.onLoad()
    }, [])

    if (!props.data && !props.loading) {
        window.location = "#/account/sign-in"
    } else {
        return accountPage(props.data, props.loading)
    }
}

Account.propTypes = {
    data: PropTypes.object,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired
}

const accountPage = (data, loading) => {
    const render = data => {
        if (!data) {
            return (<div></div>)
        }
        return (
            <div className="auth-form-container row gx-5">
                <InfoSection{...data}/>
                <PersonalSection{...data}/>
            </div>
        )
    }

    return (
        <div>
            <div className="spinner">
                <ClipLoader loading={loading}/>
            </div>
            { render(data) }
        </div>
    )
}

export default Account
