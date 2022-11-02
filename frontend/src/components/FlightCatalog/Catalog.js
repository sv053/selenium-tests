import PropTypes from "prop-types"
import {useEffect} from "react"
import ClipLoader from "react-spinners/ClipLoader";
import CatalogItem from "./CatalogItem/CatalogItem";

const Catalog = props => {
    useEffect(() => {
        props.onLoad()
    }, [])

    if ((!props.items || props.items.length === 0) && !props.loading) {
        return emptyList()
    } else {
        const items = props.items.map(item => {
            return {
                id: item.id,
                airline: item.airline.name,
                image: item.airline.image.data,
                origin: item.from.country + ", " + item.from.airport,
                destination: item.to.country + ", " + item.to.airport,
                dateTime: item.dateTime
            }
        })
        return list(items, props.loading)
    }
}

Catalog.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired,
}


const emptyList = () => {
    return (
        <div className="container-fluid align-content-center">
            <div className="row">
                <div className="text-center text">No flights</div>
            </div>
        </div>
    );
}

const list = (items, isLoading) => {
    return (
        <div>
            <div className="spinner">
                <ClipLoader loading={isLoading}/>
            </div>
            <section className="section-flights">
                <div className="container">
                    <div className="row">
                        { items.map((item, index) => {
                            return (
                                <div key={index} className="col-md-6 col-lg-3">
                                    <CatalogItem{...item}/>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </section>
        </div>
    );
}

export default Catalog
