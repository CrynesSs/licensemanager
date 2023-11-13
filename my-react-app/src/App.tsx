import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from "./Home";
import ClientList from "./components/usergui/ClientList";
import LoginPage from "./components/login/LoginPage";

const App: React.FC = ()=>{
    return (
        <Router>
            <Routes>
                <Route path='/' element={<Home/>}/>
                <Route path='/login' element={<LoginPage/>}></Route>
                <Route path='/clients' element={<ClientList/>}/>
                {/*<Route path="/clients/new" element={<AddClientForm isOpen onAdd={} onClose={}/>} />
                <Route path="/clients/edit/:id" element={<EditClientForm initialData={}/>} />*/}
            </Routes>
        </Router>
    )
}

export default App;
