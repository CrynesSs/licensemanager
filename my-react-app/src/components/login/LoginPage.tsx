// LoginComponent.jsx
import React, { useState } from 'react';

const LoginPage:React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const handleLogin = () => {
        // Check credentials (this is a simple example, replace it with your authentication logic)
        if (username === 'exampleUser' &&password === 'examplePassword') {
            // Successful login
            setError('kek');
            alert('Login successful!');
        } else {
            // Failed login
            setError('Invalid username or password');
        }
    };
    return (
        <div>
            <h2>Login</h2>
            {error && <div style={{ color: 'red' }}>{error}</div>}
            <form>
                <div>
                    <label>Username:</label>
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                </div>
                <div>
                    <label>Password:</label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </div>
                <button type="button" onClick={handleLogin}>
                    Login
                </button>
            </form>
        </div>
    );
};

export default LoginPage;