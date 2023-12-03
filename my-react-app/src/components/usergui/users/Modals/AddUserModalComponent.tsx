import React from "react";


const AddUserModalComponent: React.FC<{closeModal:()=>void}> = ({closeModal}) => {
    const handleClose = () => {
        closeModal();
    };

    return (
        <div className="modal-overlay" onClick={(event) => {
            handleClose();
            event.stopPropagation()
        }}>
            <div className="modal-content">
                <button onClick={(event) => {
                    handleClose();
                    event.stopPropagation()
                }}>Close Modal
                </button>
                {/* Other content for your modal */}
            </div>
        </div>
    );
}
export default AddUserModalComponent