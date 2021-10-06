import React from "react"

function InputField(props) {
  const inputType = props.data.type
  const labelText = props.data.label

  function handleChange(event) {
    console.log("Changing")
    props.data.setState(event.target.value)
  }

  return (
    <div className="inputBox">
      <p className="inputHeader">{labelText}</p>
      <input className="field" type={inputType} onChange={handleChange}></input>
    </div>
  )
}

export default InputField
