import PropTypes from "prop-types"
import {useEffect} from "react"
import ClipLoader from "react-spinners/ClipLoader"
import Ticket from "../Ticket/Ticket"

import './TicketList.css'

const TicketList = props => {
    useEffect(() => {
        props.onLoad(props.flightId)
    }, [])

    if ((!props.items || props.items.length === 0) && !props.loading) {
        return emptyList()
    } else {
        return list(props.items, props.flightId, props.loading, props.onOrderClick)
    }
}

TicketList.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    flightId: PropTypes.string.isRequired,
    onLoad: PropTypes.func.isRequired,
    onOrderClick: PropTypes.func.isRequired
}

 const emptyList = () => {
    return (
        <div className="container-fluid align-content-center">
            <div className="row">
                <div className="text-center text">No tickets</div>
            </div>
        </div>
    );
}

const list = (items, flightId, isLoading, onOrderClick) => {
    return (
        <div>
            <div className="spinner">
                <ClipLoader loading={isLoading}/>
            </div>
            <section className="section-tickets">
                <div className="container">
                    <div className="row">
                        { items.map((item, index) => {
                            return (
                                <div key={index} className="col-md-6 col-lg-3">
                                    <Ticket id={item.id}
                                            flightId={flightId}
                                            airline={item.flight.airline.name}
                                            origin={item.flight.from.country + ", " + item.flight.from.airport}
                                            destination={item.flight.to.country + ", " + item.flight.to.airport}
                                            dateTime={item.flight.dateTime}
                                            price={item.price}
                                            luggageAllowed={item.luggageAllowed}
                                            onOrderClick={onOrderClick}/>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </section>
        </div>
    );
}

export default TicketList
