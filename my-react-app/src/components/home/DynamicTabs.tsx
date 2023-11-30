import React, {useEffect, useState} from "react";
import "../usergui/userguistyles/DynamicTabsStyle.css"
import {ButtonGroup} from "reactstrap";

interface DynamicTabsProbs {
    componentList: React.FC[],
    onRemove: (index: number) => void;
}

const DynamicTabs: React.FC<DynamicTabsProbs> = ({componentList,onRemove}, ref) => {
    const [activeTab, setActiveTab] = useState<number>(0);
    const selectedComponent = componentList[activeTab]
    const handleRemoveClick = (index: number) => {
        onRemove(index);
        console.log("COmponentlistlength",componentList.length)
        if(activeTab == componentList.length-1){
            setActiveTab(activeTab-1 > 0 ? activeTab-1 : 0)
        }
        console.log("activeTab",activeTab)
        // Optionally, update the activeTab if needed
        // For example, you may want to select a different tab if the active one is removed
        // setActiveTab(/* logic to determine the new activeTab */);
    }
    console.log(activeTab)
    return (
        <div className={"dynamicTabsContainer"}>
            <div className={"tabsContainer"}>
                {componentList.map((comp: React.FC, ind: number) => (
                    <ButtonGroup key={ind}>
                        <button id={`Tab${ind}`} className={`tab ${activeTab == ind ? "active":""}`} onClick={() => {
                            setActiveTab(ind)
                            let tabs = Array.from(document.getElementsByClassName("tab"))
                            tabs.forEach(t => {
                                t.classList.remove("active")
                            })
                            let doc = document.getElementById(`Tab${ind}`)
                            console.log(doc,ind)
                            if (doc) {
                                doc.classList.add("active")
                            }
                        }}>{comp.displayName}</button>
                        <button className={"Remove tab"} id={`RemoveTab${ind}`} onClick={()=> {
                            handleRemoveClick(ind)
                        }}>Remove</button>
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