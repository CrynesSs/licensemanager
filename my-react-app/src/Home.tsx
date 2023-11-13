import React from "react";
import AppNavbar from "./AppNavBar";
import {Button, Container} from "reactstrap";
import {Link} from "react-router-dom";

const Home:React.FC = ()=>{
    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <Button color="link"><Link to="/clients">Clients</Link></Button>
            </Container>
        </div>
    );
}
export default Home;