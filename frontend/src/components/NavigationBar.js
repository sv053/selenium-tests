import Container from 'react-bootstrap/Container'
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import { Link } from 'react-router-dom'

const NavigationBar = () => {
    return (
        <Navbar bg="dark" variant="dark" id="nav-bar">
            <Container>
                <Link to="/" className="navbar-brand" id="main-link">AirTickets</Link>
                <Nav className="me-auto">
                    <Link to="/cart" className="nav-link" id="cart-link">Cart</Link>
                    <Link to="/account" className="nav-link" id="account-link">Account</Link>
                </Nav>
            </Container>
        </Navbar>
    );
}

export default NavigationBar
