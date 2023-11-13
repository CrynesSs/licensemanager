import React, {useEffect, useState} from 'react';
import {Button, ButtonGroup, Container} from 'reactstrap';
import {Link, useNavigate} from 'react-router-dom';
import AppNavbar from "../../AppNavBar";
import EditClientForm from "./EditClientForm";
export interface Client {
    id: React.Key;
    username: string;
    email: string;
}

const ClientList: React.FC = () => {
    const navigate = useNavigate();
    const [clients, setClients] = useState<Client[]>([]);
    const [editClient, setEditClient] = useState<Client | null>(null);

    useEffect(() => {
        fetch('/api/users')
            .then((response) => response.json())
            .then((data) => setClients(data));
    }, []);


    const remove = async (id: React.Key) => {
        await fetch(`/api/users/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        }).then(() => {
            const updatedClients = clients.filter((i:Client) => i.id !== id);
            setClients(updatedClients);
        });
    };
    const handleEditClick = (client: Client) => {
        setEditClient(client);
        // Navigate to the edit form route
        navigate(`/clients/edit/${client.id}`);
    };

    const handleEditFormSubmit = (editedClient: Client) => {
        // Handle the submission of the edited client data
        // For simplicity, this example just sets the edited client data locally
        const updatedClients = clients.map(client =>
            client.id === editedClient.id ? { ...client, ...editedClient } : client
        );
        setClients(updatedClients);
        setEditClient(null);
    };

    const handleEditFormClose = () => {
        setEditClient(null);
        // Navigate back to the client list
        navigate('/clients');
    };
    const clientList = clients.map((client) => (
        <div key={client.id} className="client-card">
            <div className="client-field">{client.username}</div>
            <div className="client-field">{client.email}</div>
            <ButtonGroup className={"client-field"}>
                <Button color="primary" onClick={() => navigate("/clients/new")}>
                    Edit
                </Button>
                <Button color="danger" onClick={() => remove(client.id)}>
                    Delete
                </Button>
            </ButtonGroup>
        </div>
    ));

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-right">
                    <Button color="success" tag={Link} to="/clients/new">
                        Add Client
                    </Button>
                </div>
                <div className={"client-list-header"}>
                    <div className={"client-field"}>
                        Username
                    </div>
                    <div className={"client-field"}>
                        Email
                    </div>
                    <div className={"client-field"}>
                    </div>
                </div>
                <div className="client-list">{clientList}</div>
                {/* EditClientForm component */}
                {editClient && (
                    <EditClientForm
                        isOpen={true}
                        initialData={editClient}
                        onSave={handleEditFormSubmit}
                        onClose={handleEditFormClose}
                    />
                )}
            </Container>
        </div>
    );
};

export default ClientList;