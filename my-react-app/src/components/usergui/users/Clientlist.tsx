import React from 'react';
import {Button, ButtonGroup} from 'reactstrap';
import "../userguistyles/ClientListStyle.css"

export interface Client {
    id: React.Key;
    username: string;
    email: string;
}

export interface clientListProbs {
    clients: Client[]
}

const ClientList: React.FC<clientListProbs> = ({clients}) => {
        const clientList = clients.map((client) => (
            <div key={client.id} className="client-card">
                <div className="client-field">{client.username}</div>
                <div className="client-field">{client.email}</div>
                <ButtonGroup className={"client-field"}>
                    <Button color="primary">
                        Edit
                    </Button>
                    <Button color="danger">
                        Delete
                    </Button>
                </ButtonGroup>
            </div>
        ));
        return (
            <div className="client-list">{clientList}</div>
        );
    }
;

export default ClientList;