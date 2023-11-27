import React, {useEffect, useState} from "react";
import './homestyle.css'
import {useNavigate} from "react-router-dom";
import axios from "axios";


const Home: React.FC = () => {
    const [authenticated, setAuthenticated] = useState<boolean>(false);
    const navigate = useNavigate();
    useEffect(() => {
        const jwtToken = localStorage.getItem('jwtToken');
        console.log(jwtToken)
        if (!jwtToken) return;
        setAuthenticated(true);
    })
    const onClickHandler = () => {
        navigate("/login")
    }

    const getUserData = () => {
        const apiUrl = 'http://localhost:8080/user';
        axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('jwtToken')}`;
        // Set up the axios request with the Authorization header
        axios.get("http://localhost:8080/api/users")
            .then(response => {
                console.log('Data:', response.data);
                response.data.forEach((d: any)=>console.log(d))
            })
            .catch(error => {
                console.error('Error:', error.message);
            });
    }
    return (
        <div className="home-component">
            {/* Top Bar */}
            <div className="top-bar">
                {!authenticated &&
                    <div className="top-bar-item top-bar-login-container">
                        <button className={"login-redirect"} onClick={onClickHandler}>
                            Login
                        </button>
                    </div>
                }
                <div className={`top-bar-item authStatus ${authenticated ? "authenticated" : ""}`}>
                    Auth-Status: {authenticated ? "Authenticated" : "Missing"}
                </div>

            </div>
            <div className="main">
                {/* Sidebar */}
                <div className="sidebar">
                    <div className="sidebar-item" onClick={getUserData}>Item 1</div>
                    <div className="sidebar-item">Item 2</div>
                    <div className="sidebar-item">Item 3</div>
                    <div className="sidebar-item">Item 4</div>
                </div>
                {/* Main Content */}
                <div className="main-content">
                    {/* SVG Animation */}
                    <div className="svg-animation">
                        {/* Your SVG Animation Goes Here */}
                        {/* Example: <svg>...</svg> */}
                    </div>
                </div>
            </div>
        </div>
    );
}
export default Home;