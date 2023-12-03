import React from 'react';
import "../userguistyles/ClientListStyle.css"
import ClientCardComponent from './ClientCardComponent';

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

export interface clientListProbs {
    clients: Client[]
}

const ClientListComponent: React.FC<clientListProbs> = ({clients}) => {
        const clientList = clients.map((client) => (
            <ClientCardComponent client={client}/>
        ));
        return (
            <div className="client-list">{clientList}</div>
        );
    }
;

export default ClientListComponent;