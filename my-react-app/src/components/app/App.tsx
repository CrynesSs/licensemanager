import React, {Suspense} from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from "../home/Home";
import ClientList from "../usergui/ClientList";
import LoginPage from "../login/LoginPage";

const App: React.FC = () => {
    return (
        <Router>
            <Suspense fallback={<div>Loading...</div>}>
                <Routes>
                    <Route path="/home" element={<Home />} />
                    <Route path="/" element={<LoginPage />} />
                    <Route path="/clients" element={<ClientList />} />
                    {/* Additional routes */}
                </Routes>
            </Suspense>
        </Router>
    );
};

const onLoginSuccess = (token: String)=>{

}

export default App;
