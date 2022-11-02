import {useEffect} from "react"
import PropTypes from "prop-types"
import ClipLoader from "react-spinners/ClipLoader"
import InfoSection from "./sections/InfoSection"
import PersonalSection from "./sections/PersonalSection"
import FooterSection from "./sections/FooterSection"

import './Account.css'

const Account = props => {
    useEffect(() => {
        props.onLoad()
    }, [])

    if (!props.data && !props.loading) {
        window.location = "#/account/sign-in"
    } else {
        return accountPage(props.data, props.loading, props.onSignOut)
    }
}

Account.propTypes = {
    data: PropTypes.object,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired,
    onSignOut: PropTypes.func.isRequired
}

const accountPage = (data, loading, onSignOut) => {
    const render = data => {
        if (!data) {
            return (<div></div>)
        }
        return (
            <div className="account-container row m-lg-3">
                <InfoSection{...data}/>
                <PersonalSection{...data}/>
                <FooterSection onSignOut={onSignOut}/>
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
