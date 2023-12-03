import React, {useState} from "react";
import {Button, ButtonGroup} from "reactstrap";
import {Client} from "./ClientListComponent";
import EditClientModalComponent from "./Modals/EditClientModalComponent";


const ClientCardComponent: React.FC<{ client: Client }> = ({client}) => {
    const [isEditModalOpen, setIsEditModalOpen] = useState<boolean>(false)
    const openModal = (event: { stopPropagation: () => void; }) => {
        setIsEditModalOpen(true)
        event.stopPropagation()
        console.log(client)
    };

    const closeModal = () => {
        setIsEditModalOpen(false);
        console.log(isEditModalOpen)
    };
    return (
            <div key={client.id} className="client-card">
                <div className="client-field">{client.username}</div>
                <div className="client-field">{client.email}</div>
                <ButtonGroup className={"client-field"}>
                    <Button color="primary" onClick={openModal}>
                        Edit
                    </Button>
                    <Button color="danger" onClick={(event) => {
                        event.stopPropagation()
                    }}>
                        Delete
                    </Button>
                </ButtonGroup>
                {isEditModalOpen && <EditClientModalComponent client={client} closeModal={closeModal}/>}
            </div>
    )
}

/**
 * <Modal key={client.id + "modal"} isOpen={isEditModalOpen} onClose={closeModal} className={"clientCardEditModal"}>
 *                 <div className={"clientListModalEdit"}>
 *                     this is modal edit kekw from client
 *                 </div>
 *             </Modal>
 */

export default ClientCardComponent;