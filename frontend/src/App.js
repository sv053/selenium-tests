import {HashRouter, Route, Routes, Navigate} from "react-router-dom"
import {useState} from "react";
import NotificationContainer from "react-notifications/lib/NotificationContainer"
import NavigationBar from "./components/NavigationBar"
import AccountPage from "./pages/AccountPage";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import ErrorPage from "./pages/ErrorPage";
import Footer from "./components/Footer/Footer";
import {getUserByEmail, postUser} from "./api/UserApi";

import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css'

function App() {
    const [account, setAccount] = useState({
        data: null,
        loading: false
    })

    const loadAccount = () => {
        if (account.data) {
            setAccount({data: account.data, loading: false})
        }
    }

    const signInPressed = (email, password) => {
        setAccount({data: null, loading: true})
        getUserByEmail(email, password)
            .then(data => setAccount({data: data, loading: false}))
            .then(e => window.location = "#/account")
            .catch(e => window.location = "#/error?message=" + e.message)
    }

    const signUpPressed = (formData) => {
        setAccount({data: null, loading: true})
        postUser(formData)
            .then(data => setAccount({data: data, loading: false}))
            .then(e => window.location = "#/account")
            .catch(e => window.location = "#/error?message=" + e.message)
    }

    return (
        <HashRouter>
            <NotificationContainer/>
            <div className="layout">
                <NavigationBar/>
                <Routes>
                    <Route exact path="/" element={<Navigate to="/accounts"/>}/>
                    <Route exact path="/account" element={
                        <AccountPage data={account.data}
                                     loading={account.loading}
                                     onLoad={loadAccount}/>}/>
                    <Route exact path="/account/sign-in" element={
                        <SignInPage onSubmit={signInPressed}/>}/>
                    <Route exact path="/account/sign-up" element={
                        <SignUpPage onSubmit={signUpPressed}/>}/>
                    <Route exact path="/error" element={<ErrorPage/>}/>
                    <Route exact path="*" element={<ErrorPage message="Not found"/>}/>
                </Routes>
                <Footer/>
            </div>
        </HashRouter>
    );
}

export default App;
