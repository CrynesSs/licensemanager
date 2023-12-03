import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import ClientListComponent from "./ClientListComponent";
import "../userguistyles/UserComponentStyles.css"
import CompanyComponent from "./CompanyComponent";

// Assuming this function fetches data from your API
const fetchData = async () => {
    try {
        const jwtToken = localStorage.getItem('jwtToken');
        axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
        const response = await axios.get("http://localhost:8080/api/customer/employees");
        return response.data as { [company: string]: [] };
    } catch (error) {
        console.error(error);
        throw error; // Propagate the error to the caller
    }
};

const UsersComponent: React.FC = () => {
    const [data, setData] = useState<{ [company: string]: [] }>({});


    useEffect(() => {
        const fetchDataAndSetData = async () => {
            try {
                const result = await fetchData();
                setData(result);
                console.log("Data fetched");
            } catch (error) {
                // Handle the error appropriately, e.g., display an error message
                console.error("Error in fetchDataAndSetData:");
                alert("Could not fetch Data.")
                throw error
            }
        };
        fetchDataAndSetData().then(() => console.log("Data fetched")).catch((e) => {
            console.log("Could not fetch Data")
        });
    }, []);


    const clientList = Object.keys(data).map((value, ind) => {
        return (
            <CompanyComponent value={value} clients={data[value]}/>
        )
    })

    return (
        <div className="customer-component">
            {clientList}
        </div>)
}
UsersComponent.displayName = "Users"
export default UsersComponent