import React, {useEffect, useState} from "react";


const ContractsComponent: React.FC = () => {
  const [addModalOpen, setAddModalOpen] = useState<boolean>(false)
  useEffect(()=>{
    let element = document.getElementById("addButton")
    if (element) {
      element.addEventListener("click", () => {
        setAddModalOpen(true)
      })
    }
  })
  return (<div className="customer-component">Contracts</div>)
}
ContractsComponent.displayName = "Contracts"

export default ContractsComponent