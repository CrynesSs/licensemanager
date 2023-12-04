import React, {ReactElement} from "react";
import CustomerComponent from "../../usergui/CustomerComponent";
import ContractsComponent from "../../usergui/ContractsComponent";
import InstancesComponent from "../../usergui/InstancesComponent";
import UsersComponent from "../../usergui/users/UserComponent";

import "./SideBarStyles.css"

const SideBar: React.FC<{ setCurrentComponent: (element: ReactElement, title: string) => void }> = ({setCurrentComponent}) => {
    return (
        <div className="sidebar">
            <div className="sidebar-item buttonBackground"
                 onClick={() => setCurrentComponent(<CustomerComponent/>, "Customer")}>Customers
            </div>
            <div className="sidebar-item buttonBackground"
                 onClick={() => setCurrentComponent(<ContractsComponent/>, "Contract")}>Contracts
            </div>
            <div className="sidebar-item buttonBackground"
                 onClick={() => setCurrentComponent(<InstancesComponent/>, "Instance")}>Instances
            </div>
            <div className="sidebar-item buttonBackground"
                 onClick={() => setCurrentComponent(<UsersComponent/>, "User")}>Users
            </div>
        </div>
    )
}
export default SideBar