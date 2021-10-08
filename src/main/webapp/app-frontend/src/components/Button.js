import React from "react";

function Button(props) {
    const innerText = props.data.innerText
    const componentName = props.data.name
    
    return (
        <button name={componentName} onClick={props.data.onClick}>{innerText}</button>
    )
}

export default Button