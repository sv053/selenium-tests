import PropTypes from "prop-types"
import ClipLoader from "react-spinners/ClipLoader"

import './TicketList.css'

const TicketList = props => {
    if ((!props.items || props.items.length === 0) && !props.loading) {
        return emptyList()
    } else {
        return list(props.items, props.flightId, props.loading, props.onOrderClick)
    }
}

TicketList.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
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

const list = (items, flightId, isLoading) => {
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
                                    {item}
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
