import React from "react";
import {ModalBody, ModalFooter, ModalHeader} from "react-bootstrap";


const AddUserModalComponent: React.FC<{ closeModal: () => void }> = ({closeModal}) => {
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

                </ModalBody>
                <ModalFooter>
                    <button onClick={(event) => {
                        handleClose();
                        event.stopPropagation()
                    }}>Close Modal
                    </button>
                </ModalFooter>

                {/* Other content for your modal */}
            </div>
        </div>
    );
}
export default AddUserModalComponent