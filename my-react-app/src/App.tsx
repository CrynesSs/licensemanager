import React, {Component} from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from "./Home";
import ClientList from "./ClientList";
import ClientEdit from "./ClientEdit";

class App extends Component {
    render() {
        return (
            <Router>
                <Routes>
                    <Route path='/' element={<Home/>}/>
                    <Route path='/clients' element={<ClientList/>}/>
                    <Route path='/clients/:id' element={<ClientEdit/>}/>
                </Routes>
            </Router>
        )
    }
}

export default App;
