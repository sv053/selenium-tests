import PropTypes from "prop-types"
import {useEffect} from "react"
import ClipLoader from "react-spinners/ClipLoader"
import HeaderSection from "./sections/HeaderSection"
import FlightDetailsSection from "./sections/FlightDetailsSection"
import FooterSection from "./sections/FooterSection"

import './FlightDetails.css'

const FlightDetails = props => {
    useEffect(() => {
        props.onLoad(props.id)
    }, [])

    if (!props.details && !props.loading) {
        return emptyPage()
    } else {
        return detailsPage(props.details, props.loading)
    }
}

FlightDetails.propTypes = {
    id: PropTypes.string,
    details: PropTypes.object,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired,
};

const emptyPage = () => {
    return (
        <div className="card-container container-fluid align-content-center">
            <div className="row">
                <div className="text-center text">No flight found</div>
            </div>
        </div>
    )
}

const detailsPage = (details, isLoading) => {
    const renderDetails = details => {
        if (!details) {
            return (<div></div>)
        }
        return (
            <div className="details-container row gx-5">
                <HeaderSection origin={details.from.country + ", " + details.from.airport}
                               destination={details.to.country + ", " + details.to.airport}
                               airline={details.airline.name}
                               dateTime={details.dateTime}
                               image={details.airline.image.data}/>
                <FlightDetailsSection title="From"
                                      country={details.from.country}
                                      airport={details.from.airport}
                                      gate={details.from.gate}/>
                <FlightDetailsSection title="To"
                                      country={details.to.country}
                                      airport={details.to.airport}
                                      gate={details.to.gate}/>
                <FooterSection{...details}/>
            </div>
        )
    }

    return (
        <div>
            <div className="spinner">
                <ClipLoader loading={isLoading}/>
            </div>
            { renderDetails(details) }
        </div>
    )
}

export default FlightDetails
