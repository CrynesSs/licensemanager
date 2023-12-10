import React, {useEffect, useState} from "react";
import "../userguistyles/UserComponentStyles.css"
import CompanyComponent from "./Components/CompanyComponent";
import AddUserModalComponent from "./Modals/AddUserModalComponent";
import {useNavigate} from "react-router-dom";
import HomeUtility from "../../home/HomeUtility";
import {AxiosError} from "axios";
import {backend} from "../../../index";

// Assuming this function fetches data from your API

const UsersComponent: React.FC = () => {
    const [data, setData] = useState<{ [company: string]: [] }>({});
    const [addModalOpen, setAddModalOpen] = useState<boolean>(false)

    const navigate = useNavigate()
    let element = document.getElementById("addButton")
    if (element) {
        element.addEventListener("click", () => {
            setAddModalOpen(true)
        })
    }
    useEffect(() => {
        console.log("In UseeffectUserCompoennt")
        const fetchDataAndSetData = async () => {
            try {
                const url = backend + "/api/customer/employees"
                const response = await HomeUtility.executeGet(url);
                setData(response.data as { [company: string]: [] });
            } catch (e: any) {
                throw e;
            }
        };
        fetchDataAndSetData().then().catch((e : AxiosError) => {
            if(e.code === "ERR_NETWORK"){
                alert("Server currently unavailable")
            }
            else if(!e.response){
                alert("Server currently unavailable")
            }
            else if (e.response!.status == 401) {
                navigate("/login")
            } else if (!e.response.status.toString().startsWith("5")) {
                alert("Something went wrong")
            } else {
                alert("Server currently unavailable")
            }
        })
    }, []);


    const clientList = Object.keys(data).map((value) => {
        return (
            <CompanyComponent value={value} clients={data[value]} key={value}/>
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