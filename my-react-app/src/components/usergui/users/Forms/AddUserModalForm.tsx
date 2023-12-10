import React, {useState} from "react";
import {Button, FloatingLabel, Form, FormControl, FormGroup, FormText} from "react-bootstrap";

import "./AddUserModalStyle.css"
import {checkJWTToken} from "../../../app/App";
import axios, {AxiosResponse} from "axios";
import {useNavigate} from "react-router-dom";
import HomeUtility from "../../../home/HomeUtility";

const validator = require('validator')

const AddUserModalForm: React.FC<{ companies: string[] }> = ({companies}) => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        phone1: '',
        phone2: '',
        firstName: '',
        lastName: '',
        company: ''
    });
    const [formErrors, setValidationErrors] = useState({
        username: 'none',
        password: 'none',
        email: 'none',
        phone1: 'none',
        phone2: 'none',
        firstName: 'none',
        lastName: 'none',
        company: 'none'
    })
    const [validated, setValidated] = useState<boolean>(false)
    const navigate = useNavigate()

    const isValidStringLength = (value: string, min: number, max: number) => {
        if (!validator.isLength(value, {min: 8})) {
            return "Input too Short"
        } else if (!validator.isLength(value, {max: 32})) {
            return "Input too Long"
        }else{
            return true;
        }
    }
    const validateUsername = async (value: string) => {
        const checkIfUserNameExists = async () => {
            const url = `http://localhost:8080/api/employees/${value}`
            const jwtToken = localStorage.getItem('jwtToken');
            axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
            let returnValue = false
            HomeUtility.executeGet(url)
                .then((res: AxiosResponse) => {
                    if (res.status == 200) {
                        returnValue = true
                    }
                }).catch(() => {
                return true;
            });
            return returnValue
        }
        if (!validator.isLength(value, {min: 8})) {
            return "Input too Short"
        } else if (!validator.isLength(value, {max: 32})) {
            return "Input too Long"
        } else if (!validator.isAlphanumeric(value, "de-DE")) {
            return "Input contains illegal Characters"
        } else if (await checkIfUserNameExists()) {
            return "Username already taken"
        } else {
            return ""
        }
    }

    const validateField = async (fieldName: string, value: string) => {
        switch (fieldName) {
            case "username" :
                return await validateUsername(value)
        }
    }

    function checkAndSetError(fieldName: string, value: string) {
        validateField(fieldName, value)
            .then(s => {
                setValidationErrors((prevErrors) => ({
                    ...prevErrors,
                    [fieldName]: s,
                }));
            })
    }

    const checkAllValid = () => {
        let errors = Object.values(formErrors)
        console.log(errors)
        let remainingErrors = errors.filter(e => e !== "")
        setValidated(remainingErrors.length == 0)
    }
    const handleChange = (e: { target: { name: string; value: string; }; }) => {
        const {name, value} = e.target;
        setFormData((prevData: any) => ({
            ...prevData,
            [name]: value,
        }));
        checkAndSetError(name, value)
        checkAllValid()
    };
    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault()
        if (!validated) {
            alert("Invalid Form")
            return
        }
        if (checkJWTToken()) {
            const jwtToken = localStorage.getItem('jwtToken');
            axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
            const response = await axios.postForm("http://localhost:8080/api/customer/employees", formData);
            return true
        } else {
            alert("Unauthorized, please log in")
            navigate("/login")
        }
        // Add your form submission logic here
        console.log('Form submitted:', formData);
    };

    const companySelect = companies.map(c => {
        return (
            <option value={c} key={c}>
                {c}
            </option>
        )
    })

    return (
        <>
            <Form onSubmit={handleSubmit} noValidate validated={validated}>
                <FormGroup>
                    <FloatingLabel label={""} controlId={"floatingUsername"}>
                        <FormControl type="text" name="username" placeholder={"Username"} onChange={handleChange}
                                     isValid={formErrors.username === ""}/>
                        <FormText>{!validator.contains(formErrors.username, "none") && formErrors.username}</FormText>
                    </FloatingLabel>
                    <FloatingLabel controlId="floatingPassword" label="">
                        <FormControl type="password" placeholder="Password" isValid={formErrors.password === ""}/>
                        <FormText>{!validator.contains(formErrors.password, "none") && formErrors.password}</FormText>
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
                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </>
    )
}

export default AddUserModalForm