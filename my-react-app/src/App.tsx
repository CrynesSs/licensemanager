import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from "./Home";
import ClientList from "./ClientList";

const App: React.FC = ()=>{
    return (
        <Router>
            <Routes>
                <Route path='/' element={<Home/>}/>
                <Route path='/clients' element={<ClientList/>}/>
            </Routes>
        </Router>
    )
}

export default App;
