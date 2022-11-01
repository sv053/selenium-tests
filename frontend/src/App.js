import {HashRouter, Route, Routes, Navigate} from "react-router-dom"
import {useState} from "react";
import NotificationContainer from "react-notifications/lib/NotificationContainer"
import NavigationBar from "./components/NavigationBar"
import AccountPage from "./pages/AccountPage";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import ErrorPage from "./pages/ErrorPage";

import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css'

function App() {

    const [account, setAccount] = useState({
        data: null,
        loading: false
    })

    const loadAccount = () => {
        console.log("Account opened")
    }

    const signInPressed = (email, password) => {
        console.log("Sign in: " + email + " " + password)
    }

    const signUpPressed = (formData) => {
        console.log("Sign up: " +
            formData.email + " " +
            formData.password + " " +
            formData.repeatPassword + " " +
            formData.name + " " +
            formData.nationality + " " +
            formData.passportNumber + " " +
            formData.age)
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
            </div>
        </HashRouter>
    );
}

export default App;
