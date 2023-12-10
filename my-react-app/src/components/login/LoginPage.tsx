// LoginComponent.jsx
import React, {useEffect, useState} from 'react';

import './loginstyle.css'
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {backend} from "../../index";

const LoginPage:React.FC =  () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    useEffect(()=>{
        if(localStorage.getItem("jwtToken")){
            navigate('/')
        }
    })
    const handleLogin = async () => {
        try {
            const apiUrl = backend+"/token";
            axios.post(apiUrl, null, {
                auth: {
                    username: username,
                    password: password
                }
            })
                .then(response => {
                    console.log('Response:', response.data);
                    // Set the token in local storage
                    localStorage.setItem('jwtToken', response.data);
                    navigate("/")
                })
                .catch(error => {
                    setError("Invalid Credentials")
                    console.error('Error:', error.message);
                });
        } catch (error) {
            // Failed login
            console.log(error)
            setError('Invalid username or password');
        }
    };
    return (
        <div className='login-component'>
            <div className={"login-container"}>
                <h2>Login</h2>
                {error && <div style={{color: 'red'}}>{error}</div>}
                <form>
                    <div className='login-file'>
                        <label>Username:</label>
                        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
                    </div>
                    <div className='login-file'>
                        <label>Password:</label>
                        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                    </div>
                    <button type="button" onClick={handleLogin}>
                        Login
                    </button>
                </form>
            </div>
            </div>

    );
};

export default LoginPage;