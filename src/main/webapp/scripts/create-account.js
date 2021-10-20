document.querySelector("#btn-back").onclick = function(event) {
    event.preventDefault()
    console.log("CLICKED BACK")
    document.location.href = "../index.jsp"
}

document.querySelector("#btn-create-account").onclick = function() {
    // TODO: servlet call
    alert("CREATED")
}