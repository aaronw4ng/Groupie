import React from "react";

function Button(props) {
    const innerText = props.data.innerText
    return (
        <button onClick={props.data.onClick}>{innerText}</button>
    )
}

export default Button