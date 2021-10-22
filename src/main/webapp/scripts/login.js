function handleLoginClick(event) {
    event.preventDefault()
    const usernameInput = document.querySelector("#input-username").value
    const passwordInput = document.querySelector("#input-password").value

    console.log(usernameInput + " " + passwordInput)
    
    console.log("Logging in")

    $.ajax({
        method: "GET",
        url : "login",
        data : {
            username : usernameInput,
            password : passwordInput
        },
        
        success : function (result) {
            console.log(result)
            if (result == "true") {
                alert("SUCCESS")
                document.location.href = "./pages/create-proposal.jsp"
            }
            else {
                alert("FAILED")
            }
            
        }
    })


    //  const servletURL = "login"
    //  const postBody = {
    //    username: usernameInput,
    //    password: passwordInput
    //  }
    //  const requestMetadata = {
    //    method: "GET",
    //    headers: {
    //      "Content-Type": "application/json"
    //    },
    //  }
    //  fetch(servletURL, requestMetadata)
    //     .then(data => console.log(data))
    
}

document.querySelector("#btn-create-page").onclick = function() {
    console.log("CLICKED")
    document.location.href = "./pages/create-account.jsp"
}
