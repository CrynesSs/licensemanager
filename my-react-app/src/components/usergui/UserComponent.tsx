import React, {useEffect, useState} from "react";
import axios from "axios";
import ClientList from "./ClientList";


const UsersComponent: React.FC = () => {
    useEffect(() => {

    })
    const getUserData = () => {
        const apiUrl = 'http://localhost:8080/api/user';
        axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('jwtToken')}`;
        // Set up the axios request with the Authorization header
        axios.get(apiUrl)
            .then(response => {
                console.log('Data:', response.data);
                response.data.forEach((d: any) => console.log(d))
            })
            .catch(error => {
                console.error('Error:', error.message);
            });
    }
    return (<div className="customer-component">
        <div>
            <ClientList/>
        </div>
        users</div>)
}
UsersComponent.displayName = "Users"
export default UsersComponent