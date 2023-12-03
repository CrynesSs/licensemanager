import React from "react";
import {Client} from "../ClientListComponent";
import {Form, FormGroup} from "reactstrap";
import {FormControl, FormLabel, Row} from "react-bootstrap";


const EditClientModalForm: React.FC<{ client: Client }> = ({client}) => {
    return (
        <div>
            <Form>
                <FormGroup row={true}>
                    <FormLabel>
                        Username
                    </FormLabel>
                    <FormControl plaintext readOnly defaultValue={client.username}></FormControl>
                </FormGroup>
            </Form>
        </div>
    )
}

export default EditClientModalForm