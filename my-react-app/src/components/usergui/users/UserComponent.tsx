import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import ClientList from "./Clientlist";
import "../userguistyles/UserComponentStyles.css"

// Assuming this function fetches data from your API
const fetchData = async () => {
    const jwtToken = localStorage.getItem('jwtToken');
    axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
    return (await axios.get("http://localhost:8080/api/customer/employees")).data as { [company: string]: []};
};

const UsersComponent: React.FC = () => {
    const navigate = useNavigate();
    const [data, setData] = useState<{ [company: string]: [] }>({});
    const [expandedCompanies,setExpandedCompanies] = useState<string[]>([])

    useEffect(() => {
        const fetchDataAndSetData = async () => {
            const apiData = await fetchData();
            setData(apiData);
        };
        fetchDataAndSetData().then(() => console.log("Data fetched"));
    }, []);

    const clientList = Object.keys(data).map((value,ind) => {
        console.log(data[value])
        return (
            <div id={value+ind} className={"companyContainer"} onClick={(d)=> {
                ((d.target as HTMLDivElement).classList.toggle("expanded"))
                if(expandedCompanies.includes(value)){
                    setExpandedCompanies(expandedCompanies.filter((s)=>s !== value))
                }else{
                    setExpandedCompanies([...expandedCompanies,value])
                }
            }}>
                <div className={"companyHeader"}>
                    <div className={"nameContainer"}>
                        {"Company : "+value}
                    </div>

                </div>
                <div className={"clientContainer"}>
                    {expandedCompanies.includes(value) && <ClientList clients={data[value]}/>}
                </div>

            </div>
        )})

    return (
        <div className="customer-component">
            {clientList}
        </div>)
}
UsersComponent.displayName = "Users"
export default UsersComponent