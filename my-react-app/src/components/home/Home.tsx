import React, {useEffect, useState} from "react";
import './homestyle.css'
import {useNavigate} from "react-router-dom";
import DynamicTabs from "./DynamicTabs";
import CustomerComponent from "../usergui/CustomerComponent";
import InstancesComponent from "../usergui/InstancesComponent";
import ContractsComponent from "../usergui/ContractsComponent";
import UsersComponent from "../usergui/users/UserComponent";
import {jwtDecode} from "jwt-decode";

const Home: React.FC = () => {
    const [authenticated, setAuthenticated] = useState<boolean>(false);
    const [currentComponents, addComponent] = useState<React.FC[]>([])
    const navigate = useNavigate();
    useEffect(() => {
        const jwtToken = localStorage.getItem('jwtToken');
        if (!jwtToken) return;
        let decodedToken = jwtDecode(jwtToken);
        if(!decodedToken)return;
        console.log("Decoded Token", decodedToken);
        let currentDate = new Date();
        // JWT exp is in seconds
        if (decodedToken.exp! * 1000 < currentDate.getTime()) {
            console.log("Token expired.");
            localStorage.setItem("jwtToken","")
        } else {
            console.log("Valid token");
            setAuthenticated(true);
        }

    }, [])

    const enum SIDEBARITEM {
        CUSTOMERS,
        CONTRACTS,
        INSTANCES,
        USERS
    }

    const findSideBarComponent  = (sidebarItemEnum: SIDEBARITEM): React.FC => {
        switch (sidebarItemEnum) {
            case SIDEBARITEM.CUSTOMERS:
                return CustomerComponent;
            case SIDEBARITEM.CONTRACTS:
                return ContractsComponent;
            case SIDEBARITEM.INSTANCES:
                return InstancesComponent;
            case SIDEBARITEM.USERS:
                return UsersComponent;
        }
    }
    const removeComponent = (indexToRemove: number) => {
        // Logic to remove component at the specified index
        const updatedList = [...currentComponents];
        updatedList.splice(indexToRemove, 1);
        addComponent(updatedList);
    };
    function handleSidebarClick(value: SIDEBARITEM) {
        const component = findSideBarComponent(value)
        console.log(currentComponents)
        if(currentComponents.filter(f=>f.displayName ===component.displayName).length==0){
            addComponent([component,...currentComponents])
        }else{
            addComponent([component,...currentComponents.filter(f=>f.displayName!==component.displayName)])
        }
    }

    // @ts-ignore
    return (
        <div className="home-component">
            {/* Top Bar */}
            <div className="top-bar">
                {!authenticated &&
                    <div className="top-bar-item top-bar-login-container">
                        <button className={"login-redirect"} onClick={() => navigate("/login")}>
                            Login
                        </button>
                    </div>
                }
                <div className={`top-bar-item authStatus ${authenticated ? "authenticated" : ""}`}>
                    Auth-Status: {authenticated ? "Authenticated" : "Missing"}
                </div>
            </div>
            <div id="main" className={"main"}>
                {/* Sidebar */}
                <div className="sidebar">
                    <div className="sidebar-item"
                         onClick={() => handleSidebarClick(SIDEBARITEM.CUSTOMERS)}>Customers
                    </div>
                    <div className="sidebar-item"
                         onClick={() => handleSidebarClick(SIDEBARITEM.CONTRACTS)}>Contracts
                    </div>
                    <div className="sidebar-item"
                         onClick={() => handleSidebarClick(SIDEBARITEM.INSTANCES)}>Instances
                    </div>
                    <div className="sidebar-item"
                         onClick={() => handleSidebarClick(SIDEBARITEM.USERS)}>Users
                    </div>
                </div>
                {/* Main Content */}
                <div className="main-content">
                    {/* SVG Animation */}
                    {/*@ts-ignore*/}
                    <DynamicTabs componentList={currentComponents} onRemove={removeComponent}/>
                </div>
            </div>
        </div>
    );
}
export default Home;