import React, {useState} from "react";
import "../usergui/userguistyles/DynamicTabsStyle.css"
import {ButtonGroup} from "reactstrap";

interface DynamicTabsProbs {
    componentList: React.FC[]
}

const DynamicTabs: React.FC<DynamicTabsProbs> = ({componentList}, ref) => {
    const [activeTab, setActiveTab] = useState<number>(0);
    const selectedComponent = componentList[activeTab]
    return (
        <div className={"dynamicTabsContainer"}>
            <div className={"tabsContainer"}>
                {componentList.map((comp: React.FC, ind: number) => (
                    <ButtonGroup key={ind}>
                        <button id={`Tab${ind}`} className={"tab"} onClick={() => {
                            setActiveTab(ind)
                            let tabs = document.getElementsByName("tab")
                            tabs.forEach(t => {
                                t.classList.remove("active")
                            })
                            let doc = document.getElementById(`Tab${ind}`)
                            if (doc) {
                                doc.classList.add("active")
                            }
                        }}>{comp.displayName}</button>
                        <button className={"Remove"}>Remove</button>
                    </ButtonGroup>
                ))}
            </div>
            <div className={"component-content-container"}>
                {selectedComponent&&React.createElement(selectedComponent)}
            </div>
        </div>
    )
}
export default DynamicTabs