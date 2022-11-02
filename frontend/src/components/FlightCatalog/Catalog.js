import PropTypes from "prop-types"
import {useEffect} from "react"
import ClipLoader from "react-spinners/ClipLoader";
import CatalogItem from "./CatalogItem/CatalogItem";
import SearchBar from "./SearchBar/SearchBar";

const Catalog = props => {
    useEffect(() => {
        props.onLoad()
    }, [])

    if ((!props.items || props.items.length === 0) && !props.loading) {
        return emptyCatalog()
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
        return catalog(items, props.loading, props.onLoad, props.onSearch)
    }
}

Catalog.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    onSearch: PropTypes.func.isRequired,
    onLoad: PropTypes.func.isRequired,
}

const emptyCatalog = () => {
    return (
        <div className="container-fluid align-content-center">
            <div className="row">
                <div className="text-center text">No flights</div>
            </div>
        </div>
    );
}

const catalog = (items, isLoading, onLoad, onSearch) => {
    return (
        <div>
            <div className="spinner">
                <ClipLoader loading={isLoading}/>
            </div>
            <section className="card-container">
                <div className="container">
                    <SearchBar onShowAll={onLoad}
                               onSearch={onSearch}/>
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
