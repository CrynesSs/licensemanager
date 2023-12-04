import React from "react";
import {Button, ModalBody, ModalFooter, ModalHeader} from "react-bootstrap";
import AddUserModalForm from "../Forms/AddUserModalForm";


const AddUserModalComponent: React.FC<{ closeModal: () => void ,companies:string[]}> = ({closeModal,companies}) => {
    const handleClose = () => {
        closeModal();
    };

    return (
        <div className="modal-overlay" onClick={(event) => {
            handleClose();
            event.stopPropagation()
        }}>
            <div className="modal-content" onClick={(event)=>event.stopPropagation()}>
                <ModalHeader>
                    This is the Add User Modal
                </ModalHeader>
                <ModalBody>
                    <AddUserModalForm companies={companies}/>
                </ModalBody>
                <ModalFooter>
                    <Button onClick={(event) => {
                        handleClose();
                        event.stopPropagation()
                    }}>Close Modal
                    </Button>
                </ModalFooter>

                {/* Other content for your modal */}
            </div>
        </div>
    );
}
export default AddUserModalComponent