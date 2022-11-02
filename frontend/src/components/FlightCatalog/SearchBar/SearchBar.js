import PropTypes from "prop-types";
import Container from 'react-bootstrap/Container'
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import {Link} from "react-router-dom";
import {Button} from "@mui/material";

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
        <form onSubmit={event => searchPressed(event)}>
            <Navbar className="search-bar">
                <Container>
                    <Nav className="me-auto">
                        <Link className="navbar-brand"
                              onClick={props.onShowAll}>
                            All flights
                        </Link>
                        <input type="text"
                               className="form-control nav-item search-bar-input"
                               id="origin"
                               placeholder="Enter origin"/>
                        <input type="text"
                               className="form-control nav-item search-bar-input"
                               id="destination"
                               placeholder="Enter destination"/>
                        <input type="text"
                               className="form-control nav-item search-bar-input"
                               id="airline"
                               placeholder="Enter airline"/>
                        <Button className="navbar-brand"
                                type="submit">
                            Search
                        </Button>
                    </Nav>
                </Container>
            </Navbar>
        </form>
    );
}

SearchBar.propTypes = {
    onShowAll: PropTypes.func.isRequired,
    onSearch: PropTypes.func.isRequired
}

export default SearchBar
