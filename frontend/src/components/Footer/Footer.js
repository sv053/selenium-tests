import Container from "react-bootstrap/Container"
import Nav from "react-bootstrap/Nav"
import {Navbar} from "react-bootstrap"

import './Footer.css'

const Footer = () => {
    return (
        <div className="footer">
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Nav className="me-auto">
                        <div className="nav-link">>
                            Copyright 2022 Air Tickets All Rights Reserved
                        </div>
                    </Nav>
                </Container>
            </Navbar>
        </div>
    )
}

export default Footer
