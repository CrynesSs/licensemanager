import React, {useEffect, useState} from "react";
import ClientListComponent, {Client} from "./ClientListComponent";


const CompanyComponent: React.FC<{ companyName: string }> = ({companyName}) => {
    const [expanded, setExpanded] = useState<boolean>(false)

    return (
        <div className={"companyContainer"}>
            <div className={"companyHeader"}>
                <div className={"nameContainer"} onClick={() => setExpanded(!expanded)}>
                    <div className={"nameContainerText"}>
                        {"Company : " + companyName}
                    </div>
                </div>
            </div>
            <div className={"clientContainer"}>
                {expanded && <ClientListComponent companyName={companyName}/>}
            </div>
        </div>)
}

export default CompanyComponent