import React, {ReactElement, useEffect, useState} from "react";
import './homestyle.css'
import TopBar from "./components/TopBar";
import {checkJWTToken} from "../app/App";
import SideBar from "./components/SideBar";


const Home: React.FC = () => {
    const [authenticated, setAuthenticated] = useState<boolean>(false);
    const [currentComponent, setCurrentComponent] = useState<ReactElement>()
    const [topBarAdd,setTopBarAdd] = useState<string>("None")
    useEffect(() => {
        setAuthenticated(checkJWTToken() != undefined)
        console.log("In Home Useeffect")
    }, [currentComponent])

    const setComponent = (element: ReactElement,name: string) =>{
        setCurrentComponent(element)
        setTopBarAdd(name)
    }
    return (
        <>
            <div className="home-component">
                <TopBar authenticated={authenticated} toAdd={topBarAdd}/>
                <div id="main" className={"main"}>
                    <SideBar setCurrentComponent={setComponent}/>
                    <div className="main-content">
                        {currentComponent}
                    </div>
                </div>
            </div>
        </>

    );
}
export default Home;