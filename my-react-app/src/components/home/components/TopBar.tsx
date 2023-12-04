import React from "react";

import "./TopBarStyles.css"

const TopBar: React.FC<{ authenticated: boolean,toAdd:string}> = ({authenticated,toAdd}) => {
    return (
        <div className="topBar">
            <div className={"topBarLeft"}>
                {authenticated ?
                    <div className={"topBarItem authStatus buttonBackground"}>
                        Auth-Status: {"Authenticated"}
                    </div>
                    :
                    <div>

                    </div>
                }
                <div className={"topBarItem"}>

                </div>
            </div>
            <div className={"topBarRight"}>
                <div id={"addButton"} className={"topBarItem buttonBackground sidebar-item"} onClick={()=>{}}>
                    {"Add : " + toAdd}
                </div>
            </div>
        </div>

    )
}
export default TopBar