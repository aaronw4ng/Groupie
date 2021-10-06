import React from "react";


function InputField(props) {
    const inputType = props.data.type
    const labelText = props.data.label

    function handleChange(event) {
        console.log("Changing")
        props.data.setState(event.target.value)
    }

    return (
        <div>
            <p>{labelText}</p>
            <input type={inputType} onChange={handleChange}></input>
        </div>
    )
}

export default InputField