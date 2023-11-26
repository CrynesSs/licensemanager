import React from "react";
import AppNavbar from "../app/AppNavBar";
import {Container} from "reactstrap";
import ClientList from "../usergui/ClientList";

const Home: React.FC = () => {
    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <ClientList/>
            </Container>
        </div>
    );
}
export default Home;