import React, {Suspense} from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from "../home/Home";
import LoginPage from "../login/LoginPage";
import {jwtDecode} from "jwt-decode";


export const checkJWTToken = ()=>{
    const jwtToken = localStorage.getItem('jwtToken');
    if (!jwtToken) return undefined;
    let decodedToken = jwtDecode(jwtToken);
    if(!decodedToken)return undefined;
    console.log("Decoded Token", decodedToken);
    let currentDate = new Date();
    // JWT exp is in seconds
    if (decodedToken.exp! * 1000 < currentDate.getTime()) {
        console.log("Token expired.");
        localStorage.setItem("jwtToken","")
        return undefined;
    } else {
        console.log("Valid token");
        return decodedToken;
    }
}

const App: React.FC = () => {
    return (
        <Router>
            <Suspense fallback={<div>Loading...</div>}>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/login" element={<LoginPage/>}/>
                    {/* Additional routes */}
                </Routes>
            </Suspense>
        </Router>
    );
};

export default App;
