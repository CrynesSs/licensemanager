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
    const [companies, setCompanies] = useState<string[]>([]);
    const [addModalOpen, setAddModalOpen] = useState<boolean>(false)

    let element = document.getElementById("addButton")
    if (element) {
        element.addEventListener("click", () => {
            setAddModalOpen(true)
        })
    }
    useEffect(()=>{
        const url = backend + "/api/customer/companyNames"
        HomeUtility.executeGet(url).then(r=>{
            console.log(r.data)
            setCompanies(r.data)
        }).catch(e=>{
            console.log(e)
        })
    },[])





    const companyList = companies.map((companyName) => {
        return (
            <CompanyComponent  key={companyName} companyName={companyName}/>
        )
    })

    return (
        <>
            <div className="customer-component">
                {companyList}
            </div>
            <div>
                {addModalOpen &&
                    <AddUserModalComponent closeModal={() => setAddModalOpen(false)} companies={companies}/>}
            </div>
        </>

    )

}
UsersComponent.displayName = "Users"
export default UsersComponent