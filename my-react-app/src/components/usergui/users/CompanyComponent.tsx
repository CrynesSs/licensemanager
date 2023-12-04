import React, {useState} from "react";
import ClientListComponent, {Client} from "./ClientListComponent";


const CompanyComponent: React.FC<{ value: string, clients: Client[]}> = ({value, clients}) => {
    const [currentClients, setClients] = useState<Client[]>(clients)

    const [expanded, setExpanded] = useState<boolean>(false)
    return (
        <div className={"companyContainer"} onClick={() => setExpanded(!expanded)}>
            <div className={"companyHeader"}>
                <div className={"nameContainer"}>
                    <div className={"nameContainerText"}>
                        {"Company : " + value}
                    </div>
                </div>
            </div>
            <div className={"clientContainer"}>
                {expanded && <ClientListComponent clients={currentClients}/>}
            </div>
        </div>)
}

export default CompanyComponent