import PropTypes from "prop-types";
import Container from 'react-bootstrap/Container'
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import { Link } from 'react-router-dom'

import './SearchBar.css'

const SearchBar = props => {
    const searchPressed = (e) => {
        e.preventDefault()
        props.onSearch(
            e.target.elements.origin.value,
            e.target.elements.destination.value,
            e.target.elements.airline.value)
    }

    return (
        <Navbar className="search-bar">
            <Container>
                <Nav className="me-auto">
                    <Link className="nav-link"
                          onClick={props.onShowAll}>
                        All
                    </Link>
                    <input type="text"
                           className="form-control nav-item search-bar-input"
                           id="origin"
                           required="true"
                           placeholder="Enter origin"/>
                    <input type="text"
                           className="form-control nav-item search-bar-input"
                           id="destination"
                           required="true"
                           placeholder="Enter destination"/>
                    <input type="text"
                           className="form-control nav-item search-bar-input"
                           id="airline"
                           required="true"
                           placeholder="Enter airline"/>
                    <Link className="nav-link"
                           onClick={event => searchPressed(event)}>
                        Search
                    </Link>
                </Nav>
            </Container>
        </Navbar>
    );
}

SearchBar.propTypes = {
    onShowAll: PropTypes.func.isRequired,
    onSearch: PropTypes.func.isRequired
}

export default SearchBar
