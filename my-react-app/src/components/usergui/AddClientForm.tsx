// AddClientForm.jsx
import React, { useState } from 'react';
import { Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';

interface AddUserFormProps {
    isOpen: boolean;
    onAdd: (newUser: NewUser) => void;
    onClose: () => void;
}

interface NewUser {
    username: string;
    email: string;
}

const AddClientForm: React.FC<AddUserFormProps> = ({ isOpen, onAdd, onClose }) => {
    const [newUser, setNewUser] = useState<NewUser>({ username: '', email: '' });

    const handleAdd = () => {
        onAdd(newUser);
        onClose();
        // Clear the form after submission
        setNewUser({ username: '', email: '' });
    };

    return (
        <Modal isOpen={isOpen} toggle={onClose}>
            <ModalHeader>Add New User</ModalHeader>
            <ModalBody>
                <Form onSubmit={(e) => e.preventDefault()}>
                    <FormGroup>
                        <Label for="newUsername">Username</Label>
                        <Input
                            type="text"
                            id="newUsername"
                            value={newUser.username}
                            onChange={(e) => setNewUser({ ...newUser, username: e.target.value })}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Label for="newEmail">Email</Label>
                        <Input
                            type="email"
                            id="newEmail"
                            value={newUser.email}
                            onChange={(e) => setNewUser({ ...newUser, email: e.target.value })}
                        />
                    </FormGroup>
                </Form>
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={handleAdd}>
                    Add User
                </Button>
                <Button color="secondary" onClick={onClose}>
                    Cancel
                </Button>
            </ModalFooter>
        </Modal>
    );
};

export default AddClientForm;
