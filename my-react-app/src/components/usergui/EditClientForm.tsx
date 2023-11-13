
// EditClientForm.jsx
import React, { useState } from 'react';
import { Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import {Client} from "./ClientList";
import {useNavigate} from "react-router-dom";

interface EditClientFormProps {
    isOpen: boolean;
    initialData: Client;
    onSave: (editedData: Client) => void;
    onClose: () => void;
}

const EditClientForm:React.FC<EditClientFormProps> = ({ isOpen, initialData, onSave, onClose }) => {
    const [editedData, setEditedData] = useState(initialData);
    const navigate = useNavigate();
    const handleSave = () => {
        onSave(editedData);
        onClose();
    };
    const handleCancel = () => {
        onClose();
        // Navigate back to the client list
        navigate('/clients');
    };
    return (
        <Modal isOpen={isOpen} toggle={onClose} backdrop="static">
            <ModalHeader>Edit Client</ModalHeader>
            <ModalBody>
                <Form onSubmit={(e) => e.preventDefault()}>
                    <FormGroup>
                        <Label for="editUsername">Username</Label>
                        <Input
                            type="text"
                            id="editUsername"
                            value={editedData.username || ''}
                            onChange={(e) => setEditedData({ ...editedData, username: e.target.value })}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Label for="editEmail">Email</Label>
                        <Input
                            type="email"
                            id="editEmail"
                            value={editedData.email || ''}
                            onChange={(e) => setEditedData({ ...editedData, email: e.target.value })}
                        />
                    </FormGroup>
                </Form>
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={handleSave}>
                    Save Changes
                </Button>
                <Button color="secondary" onClick={onClose}>
                    Cancel
                </Button>
            </ModalFooter>
        </Modal>
    );
};

export default EditClientForm;
