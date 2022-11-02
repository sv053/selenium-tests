import PropTypes from "prop-types"
import {useEffect} from "react"
import CatalogItem from "./CatalogItem"
import FlightList from "../Flight/FlightList/FlightList";

const Catalog = props => {
    useEffect(() => {
        props.onLoad()
    }, [])

    let products = []
    props.items.forEach(item => {
        const product = <CatalogItem id={item.id}
                                     airline={item.airline.name}
                                     image={item.airline.image.data}
                                     origin={item.from.country + ", " + item.from.airport}
                                     destination={item.to.country + ", " + item.to.airport}
                                     dateTime={item.dateTime}/>
        products.push(product)
    })
    return (
        <FlightList items={products} loading={props.loading}/>
    )
}

Catalog.propTypes = {
    items: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    onLoad: PropTypes.func.isRequired,
}

export default Catalog
