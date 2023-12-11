import React, {useEffect, useState} from 'react';
import "../../userguistyles/ClientListStyle.css"
import ClientCardComponent from './ClientCardComponent';
import {backend} from "../../../../index";
import HomeUtility from "../../../home/HomeUtility";
import {AxiosError} from "axios";
import {useNavigate} from "react-router-dom";

export interface Client {
    id: React.Key;
    username: string;
    email: string;

    first_name:string;
    last_name:string;
    password:string;
    phone1:string;
    phone2:string;
}


const ClientListComponent: React.FC<{companyName:string}> = ({companyName}) => {

    const [clients,setClients] = useState<Client[]>([])
    const navigate = useNavigate()

    useEffect(() => {
        console.log("In UseeffectClientList")
        const fetchDataAndSetData = async () => {
            try {
                const url = backend+`/api/customer/employees/${companyName}`
                console.log(url)
                const response = await HomeUtility.executeGet(url);
                setClients(response.data)
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

        const clientList = clients.map((client) => (
            <ClientCardComponent client={client} key={client.username}/>
        ));
        return (
            <div className="client-list">{clientList}</div>
        );
    }
;

export default ClientListComponent;