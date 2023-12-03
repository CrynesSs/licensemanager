import React, {Suspense} from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from "../home/Home";
import LoginPage from "../login/LoginPage";
import EditClientModalComponent from "../usergui/users/Modals/EditClientModalComponent";

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
