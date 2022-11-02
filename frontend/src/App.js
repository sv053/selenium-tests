import {HashRouter, Route, Routes, Navigate} from "react-router-dom"
import {useState} from "react";
import NotificationContainer from "react-notifications/lib/NotificationContainer"
import NavigationBar from "./components/NavigationBar"
import AccountPage from "./pages/AccountPage"
import SignInPage from "./pages/SignInPage"
import SignUpPage from "./pages/SignUpPage"
import ErrorPage from "./pages/ErrorPage"
import Footer from "./components/Footer/Footer"
import FlightCatalogPage from "./pages/FlightCatalogPage"
import FlightDetailsPage from "./pages/FlightDetailsPage"
import TicketCatalogPage from "./pages/TicketCatalogPage"
import CartPage from "./pages/CartPage"
import {getUserByEmail, postUser} from "./api/UserApi"
import {getAllFlights, getFlightById} from "./api/FlightApi"
import {getAllTicketsByFlight, getTicketById} from "./api/TicketApi"

import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css'

const App = () => {
    const [flightCatalog, setFlights] = useState({
        items: [],
        loading: false,
    })
    const [flightDetails, setFlightDetails] = useState({
        details: null,
        loading: false,
    })
    const [ticketCatalog, setTickets] = useState({
        items: [],
        loading: false,
    })
    const [cart, setCart] = useState({
        items: [],
        loading: false
    })
    const [account, setAccount] = useState({
        data: null,
        loading: false
    })

    const loadFlights = () => {
        setFlights({items: [], loading: true})
        getAllFlights()
            .then(data => setFlights({items: data, loading: false}))
            .catch(e => {
                setFlights({items: [], loading: false})
                window.location = "#/error?message=" + e.message
            })
    }

    const loadFlightDetails = id => {
        setFlightDetails({details: null, loading: true})
        getFlightById(id)
            .then(data => setFlightDetails({details: data, loading: false}))
            .catch(e => {
                setFlightDetails({details: null, loading: false})
                window.location = "#/error?message=" + e.message
            })
    }

    const loadTickets = flightId => {
        setTickets({items: [], loading: true})
        getAllTicketsByFlight(flightId)
            .then(data => setTickets({items: data, loading: false}))
            .catch(e => {
                setTickets({items: [], loading: false})
                window.location = "#/error?message=" + e.message
            })
    }

    const orderTicket = ticketId => {
        setCart({items: cart.items, loading: true})
        getTicketById(ticketId)
            .then(data => addToCart(data))
            .catch(e => {
                setCart({items: cart.items, loading: false})
                window.location = "#/error?message=" + e.message
            })
    }

    const addToCart = (ticket) => {
        ticket.numberInCart = (cart.items.length === 0) ? 1 : cart.items.length + 1

        const items = [...cart.items]
        items.push(ticket)
        setCart({items: items, loading: false})
    }

    const loadCart = () => {
        console.log("Cart opened")
    }

    const removeFromCart = numberInCart => {
        console.log("Remove " + numberInCart)
        let items = cart.items.filter(item => item.numberInCart !== numberInCart)
        setCart({items: items, loading: false})
    }

    const loadAccount = () => {
        if (account.data) {
            setAccount({data: account.data, loading: false})
        }
    }

    const signIn = (email, password) => {
        setAccount({data: null, loading: true})
        getUserByEmail(email, password)
            .then(data => setAccount({data: data, loading: false}))
            .then(e => window.location = "#/account")
            .catch(e => {
                setAccount({data: null, loading: false})
                window.location = "#/error?message=" + e.message
            })
    }

    const signUp = formData => {
        setAccount({data: null, loading: true})
        postUser(formData)
            .then(data => setAccount({data: data, loading: false}))
            .then(e => window.location = "#/account")
            .catch(e => {
                setAccount({data: null, loading: false})
                window.location = "#/error?message=" + e.message
            })
    }

    const singOut = () => {
        setAccount({data: null, loading: false})
        window.location = "#/account"
    }

    return (
        <HashRouter>
            <NotificationContainer/>
            <div className="layout">
                <NavigationBar/>
                <Routes>
                    <Route exact path="/" element={<Navigate to="/flights"/>}/>
                    <Route exact path="/flights" element={
                        <FlightCatalogPage items={flightCatalog.items}
                                           loading={flightCatalog.loading}
                                           onLoad={loadFlights}/>}/>
                    <Route exact path="/flights/:id" element={
                        <FlightDetailsPage details={flightDetails.details}
                                           loading={flightDetails.loading}
                                           onLoad={loadFlightDetails}/>}/>
                    <Route exact path="/tickets" element={
                        <TicketCatalogPage items={ticketCatalog.items}
                                           loading={ticketCatalog.loading}
                                           onLoad={loadTickets}
                                           onOrderClick={orderTicket}/>}/>
                    <Route exact path="/cart" element={
                        <CartPage items={cart.items}
                                  loading={cart.loading}
                                  onLoad={loadCart}
                                  onRemove={removeFromCart}/>}/>
                    <Route exact path="/account" element={
                        <AccountPage data={account.data}
                                     loading={account.loading}
                                     onLoad={loadAccount}
                                     onSignOut={singOut}/>}/>
                    <Route exact path="/account/sign-in" element={
                        <SignInPage onSubmit={signIn}/>}/>
                    <Route exact path="/account/sign-up" element={
                        <SignUpPage onSubmit={signUp}/>}/>
                    <Route exact path="/error" element={<ErrorPage/>}/>
                    <Route exact path="*" element={<ErrorPage message="Not found"/>}/>
                </Routes>
                <Footer/>
            </div>
        </HashRouter>
    );
}

export default App;
