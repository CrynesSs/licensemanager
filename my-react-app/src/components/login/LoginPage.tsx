// LoginComponent.jsx
import React, {useState} from 'react';

import './loginstyle.css'
import axios from "axios";
import {useNavigate} from "react-router-dom";

const LoginPage:React.FC =  () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:8080/authenticate', {
                username: username,
                password: password,
            }, {
                withCredentials: true,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                }
            })
            console.log(response)
            navigate("/home")
        } catch (error) {
            // Failed login
            console.log(error)
            setError('Invalid username or password');
        }
    };
    return (
        <div className='login-container'>
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
    );
};

export default LoginPage;