import React from "react";
import "../../userguistyles/EditClientModalStyle.css"
import {Client} from "../ClientListComponent";
import {ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import EditClientModalForm from "../Forms/EditClientModalForm";

const EditClientModalComponent: React.FC<{ closeModal: () => void, client: Client }> = ({closeModal, client}) => {
    const handleClose = () => {
        closeModal();
    };

    return (
        <div className="modal-overlay" onClick={(event) => {
            handleClose();
            event.stopPropagation()
        }}>
            <div className="modal-content" onClick={(event) => event.stopPropagation()}>
                <ModalHeader>This is the EditClientFormModal</ModalHeader>
                <ModalBody>
                    <div>This is the Modal Body</div>
                    <EditClientModalForm client={client}/>
                    <button onClick={(event) => {
                        handleClose();
                        event.stopPropagation()
                    }}>Close Modal
                    </button>
                </ModalBody>
                <ModalFooter>This is the Modal footer</ModalFooter>
                {/* Other content for your modal */}
            </div>
        </div>
    );
};


export default EditClientModalComponent