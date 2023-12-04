import React, {useState} from "react";
import {Button, FloatingLabel, Form, FormControl, FormGroup} from "react-bootstrap";

import "./AddUserModalStyle.css"
const AddUserModalForm: React.FC<{ companies: string[] }> = ({companies}) => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        phone1: '',
        phone2: '',
        firstName: '',
        lastName: '',
        company: '',
    });

    const handleChange = (e: { target: { name: any; value: any; }; }) => {
        const {name, value} = e.target;
        setFormData((prevData: any) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        // Add your form submission logic here
        console.log('Form submitted:', formData);
    };
    const companySelect = companies.map(c => {
        return (
            <option value={c}>
                {c}
            </option>
        )
    })

    return (
        <>
            <FormGroup>
                <FloatingLabel label={""} controlId={"floatingUsername"}>
                    <FormControl type="text" name="username" placeholder={"Username"} onChange={handleChange}/>
                </FloatingLabel>
                <FloatingLabel controlId="floatingPassword" label="">
                    <FormControl type="password" placeholder="Password"/>
                </FloatingLabel>
                <FloatingLabel label={""} controlId={"floatingEmail"}>
                    <FormControl type="email" name="email" value={formData.email} onChange={handleChange}
                                 placeholder={"name@example.com"}/>
                </FloatingLabel>
            </FormGroup>

            <FormGroup controlId="phones">
                <FloatingLabel label={""}>
                    <FormControl type="tel" name="phone1" value={formData.phone1} onChange={handleChange}
                                 placeholder={"Phone 1"}/>
                </FloatingLabel>
            </FormGroup>
            <FormGroup controlId="phone2">
                <FloatingLabel label={""}>
                    <FormControl type="tel" name="phone2" value={formData.phone2} onChange={handleChange}
                                 placeholder={"Phone 2"}/>
                </FloatingLabel>
            </FormGroup>

            <FormGroup controlId="names">
                <FloatingLabel label={""}>
                    <FormControl type="text" name="firstName" value={formData.firstName} onChange={handleChange}
                                 placeholder={"First Name"}/>
                </FloatingLabel>
                <FloatingLabel label={""} controlId={"floatingLastName"}>
                    <FormControl type="text" name="lastName" value={formData.lastName} onChange={handleChange}
                                 placeholder={"Last Name"}/>
                </FloatingLabel>
            </FormGroup>
            <FormGroup controlId="company">
                <FloatingLabel label={""}>
                    <FormControl as="select" name="company" value={formData.company} onChange={handleChange}
                                 placeholder={"Company"}>
                        <option value="">Select a company</option>
                        {companySelect}
                    </FormControl>
                </FloatingLabel>

            </FormGroup>
            <Button variant="primary" type="submit" onClick={handleSubmit}>
                Submit
            </Button>
        </>
    )
}

export default AddUserModalForm