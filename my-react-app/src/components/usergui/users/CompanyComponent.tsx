import React, {useState} from "react";
import ClientListComponent, {Client} from "./ClientListComponent";
import AddUserModalComponent from "./Modals/AddUserModalComponent";


const CompanyComponent: React.FC<{ value: string, clients: Client[] }> = ({value, clients}) => {
    const [currentClients, setClients] = useState<Client[]>(clients)
    const [expanded, setExpanded] = useState<boolean>(false)
    const [addClientModalOpen, setAddClientModalOpen] = useState<boolean>(false)
    return (
        <div className={"companyContainer"} onClick={(event) => setExpanded(!expanded)}>
            <div className={"companyHeader"}>
                <div className={"nameContainer"}>
                    <div className={"nameContainerText"}>
                        {"Company : " + value}
                    </div>
                </div>
                <div className={"openAddUserModal nameContainer"} onClick={(event) => {
                    setAddClientModalOpen(true);
                    event.stopPropagation()
                }}>
                    <div className={"nameContainerText"}>
                        Add User
                    </div>
                </div>
            </div>
            {addClientModalOpen && <AddUserModalComponent closeModal={() => setAddClientModalOpen(false)}/>}
            <div className={"clientContainer"}>
                {expanded && <ClientListComponent clients={currentClients}/>}
            </div>
        </div>)
}

export default CompanyComponent