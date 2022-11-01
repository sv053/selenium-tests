import {HashRouter, Route, Routes, Navigate} from "react-router-dom"
import NotificationContainer from "react-notifications/lib/NotificationContainer"
import NavigationBar from "./components/NavigationBar"

import './App.css'

function App() {
    return (
        <HashRouter>
          <NotificationContainer/>
          <div className="layout">
            <NavigationBar/>
            <Routes>
              <Route exact path="/" element={<Navigate to="/flights"/>}/>
              <Route exact path="/flights" element={}/>
              <Route exact path="/account" element={}/>
              <Route exact path="/account/sign-in" element={}/>
              <Route exact path="/account/sign-up" element={}/>
              <Route exact path="/error" element={}/>
              <Route exact path="*" element={}/>
            </Routes>
          </div>
        </HashRouter>
    );
}

export default App;
