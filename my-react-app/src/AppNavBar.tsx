import {Component} from "react";
import {Navbar, NavbarBrand} from 'reactstrap';
import {Link} from "react-router-dom";

export default class AppNavbar extends Component {
    constructor(props: {} | Readonly<{}>) {
        super(props);
        this.state = {isOpen: false};
        this.toggle = this.toggle.bind(this);
    }

    toggle() {

        this.setState({
            // @ts-ignore
            isOpen: !this.state.isOpen
        });
    }

    render() {
        return <Navbar color="dark" dark expand="md">
            <NavbarBrand tag={Link} to="/">Home</NavbarBrand>
        </Navbar>;
    }
}