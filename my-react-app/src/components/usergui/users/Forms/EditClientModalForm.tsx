import React from "react";
import {Client} from "../ClientListComponent";
import {Form, FormGroup} from "reactstrap";
import {FormControl, FormLabel, InputGroup} from "react-bootstrap";
import InputGroupText from "react-bootstrap/InputGroupText";


const EditClientModalForm: React.FC<{ client: Client }> = ({client}) => {
    return (
        <div>
            <Form>
                <FormGroup row={true}>
                    <FormLabel>
                        Username
                    </FormLabel>
                    <InputGroup className={"exampleInput"}>
                        <InputGroupText>@</InputGroupText>

                        <FormControl placeholder={"Username"}></FormControl>
                    </InputGroup>
                    <FormControl plaintext readOnly defaultValue={client.username}></FormControl>
                </FormGroup>
            </Form>
        </div>
    )
}

export default EditClientModalForm