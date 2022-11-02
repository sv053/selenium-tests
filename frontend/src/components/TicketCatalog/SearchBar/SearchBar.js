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
        props.onSearch(props.flightId, e.target.elements.price.value)
    }

    return (
        <form onSubmit={event => searchPressed(event)}>
            <Navbar className="tickets-search-bar">
                <Container>
                    <Nav className="me-auto">
                        <Link className="navbar-brand"
                              onClick={props.onShowAll}>
                            All tickets
                        </Link>
                        <input type="text"
                               className="form-control nav-item tickets-search-bar-input"
                               id="price"
                               placeholder="Enter price"/>
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
    flightId: PropTypes.string.isRequired,
    onShowAll: PropTypes.func.isRequired,
    onSearch: PropTypes.func.isRequired
}

export default SearchBar
