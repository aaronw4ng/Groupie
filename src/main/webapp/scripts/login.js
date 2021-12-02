let attempt = 0;
function handleLoginClick(event) {
    event.preventDefault()
    const usernameInput = document.querySelector("#input-username").value
    const passwordInput = document.querySelector("#input-password").value
    let warningMessageContainer = document.querySelector(".warning-message")

    
    console.log("Logging in")
    if (validateFields(usernameInput, passwordInput)) {
        $.ajax({
            method: "POST",
            url : "login",
            data : {
                username : usernameInput,
                password : passwordInput
            },
            
            success : function (result) {
                // if result is -1, then login failed
                // otherwise, result is the userID
                console.log(result)
                if (result != "-1") {
                    alert("Login successful!")
                    // User session persistence
                    if (!sessionStorage.getItem("username")) {
                        sessionStorage.setItem("username", usernameInput)
                    }
                    if (!sessionStorage.getItem("userId")) {
                        sessionStorage.setItem("userId", result)
                    }
                    document.location.href = "./pages/create-proposal.jsp"
                }
                else {
                    attempt++;
                    let message = `
                    <p>Invalid username or password.</p>
                    `;
                    warningMessageContainer.innerHTML = "Attempt: " + attempt + message
                    warningMessageContainer.style.display = "block"

                    if (attempt >= 3) {
                        manageLoginAttempts()
                    }
                }
            }
        })
    }
}

// Block user for sixty seconds after three failed login attempts
function manageLoginAttempts() {
    document.querySelector("#btn-login").disabled = true;
    setTimeout(function() {
        document.querySelector("#btn-login").disabled = false;
    }, 60000);
}

function validateFields(username, password) {
    let warningMessageContainer = document.querySelector(".warning-message")
    let usernameField = document.querySelector("#input-username")
    let passwordField = document.querySelector("#input-password")
    let isValid = true

    // Clear warning container
    warningMessageContainer.innerHTML = ""

    // Empty field
    if (username.length == 0) {
        isValid = false
        usernameField.classList.add("error-input")
        let message = `
        <p name="empty-username">Username can't be empty.</p>
        `
        warningMessageContainer.innerHTML += message
    }
    if (password.length == 0) {
        isValid = false
        passwordField.classList.add("error-input")
        let message = `
        <p name="empty-password">Password can't be empty.</p>
        `
        warningMessageContainer.innerHTML += message
    }

    if (!isValid) {
        warningMessageContainer.style.display = "block"
    }
    return isValid
}

document.querySelector("#btn-create-page").onclick = function() {
    console.log("CLICKED")
    document.location.href = "./pages/create-account.jsp"
}
