import React, {useState} from "react";
import {Navbar, NavbarBrand} from 'reactstrap';
import {Link} from "react-router-dom";

const AppNavbar: React.FC = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggle = () => {
        setIsOpen((prev) => !prev);
    };

    return (
        <Navbar color="dark" dark expand="md">
            <NavbarBrand tag={Link} to="/" onClick={toggle}>
                Home
            </NavbarBrand>
        </Navbar>
    );
};

export default AppNavbar;