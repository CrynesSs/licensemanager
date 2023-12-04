import React, {useEffect, useState} from "react";
import axios from "axios";
import "../userguistyles/UserComponentStyles.css"
import CompanyComponent from "./CompanyComponent";
import AddUserModalComponent from "./Modals/AddUserModalComponent";
import {useNavigate} from "react-router-dom";

// Assuming this function fetches data from your API
const fetchData = async () => {
    try {
        const jwtToken = localStorage.getItem('jwtToken');
        axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
        const response = await axios.get("http://localhost:8080/api/customer/employees");
        return response.data as { [company: string]: [] };
    } catch (error) {
        throw error; // Propagate the error to the caller
    }
};

const UsersComponent: React.FC = () => {
    const [data, setData] = useState<{ [company: string]: [] }>({});
    const [addModalOpen, setAddModalOpen] = useState<boolean>(false)
    const navigate = useNavigate()
    useEffect(() => {
        let element = document.getElementById("addButton")
        if (element) {
            element.addEventListener("click", () => {
                setAddModalOpen(true)
            })
        }
        const fetchDataAndSetData = async () => {
            try {
                const result = await fetchData();
                setData(result);
                console.log("Data fetched");
            } catch (error) {
                // Handle the error appropriately, e.g., display an error message
                throw error
            }
        };
        fetchDataAndSetData()
            .then(() => console.log("Data fetched"))
            .catch((e) => {
                if(e.response.status ==401){
                    alert("Unauthorized, please log in")
                    navigate("/login")
                }else if(e.response.status.toString().startsWith("5")){
                    alert("Server currently unavailable")
                }

            });
    }, []);


    const clientList = Object.keys(data).map((value) => {
        return (
            <CompanyComponent value={value} clients={data[value]}/>
        )
    })

    return (
        <>
            <div className="customer-component">
                {clientList}
            </div>
            <div>
                {addModalOpen &&
                    <AddUserModalComponent closeModal={() => setAddModalOpen(false)} companies={Object.keys(data)}/>}
            </div>
        </>

    )

}
UsersComponent.displayName = "Users"
export default UsersComponent