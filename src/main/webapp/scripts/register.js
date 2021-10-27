function handleRegisterClick(event) {
  event.preventDefault()
  const usernameInput = document.querySelector("#input-username").value
  const passwordInput = document.querySelector("#input-password").value
  const rePasswordInput = document.querySelector("#re-input-password").value
  let warningMessageContainer = document.querySelector(".warning-message")

  console.log(usernameInput + " " + passwordInput + " " + rePasswordInput)

  // Validate passwords

  console.log("Creating Account...")
  if (validateFields(usernameInput, passwordInput, rePasswordInput)) {
    $.ajax({
      method: "POST",
      url: "../register",
      data: {
        username: usernameInput,
        password: passwordInput,
      },

      success: function (result) {
        console.log(result)
        if (result == "true") {
          alert("Account successfully created")
        } else {
          let usernameInputField = document.querySelector("#input-username")
          let warningMessageContainer = document.querySelector(".warning-message")
          let warningMessage = `
          <p name='username-taken'>Username has already been taken</p>
          `
          warningMessageContainer.innerHTML += warningMessage
          warningMessageContainer.style.display = "block"
          usernameInputField.classList.add("error-input")
        }
      },
    })
  }

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

// Warning Message Set Up
function setWarnings(descriptionContainer, warningContainer, inputField, message, messageName) {
  descriptionContainer.style.display = "none"
  inputField.classList.add("error-input")
  warningContainer.style.display = "block"
  let warningMessage = `
  <p name=${messageName}>${message}</p>
  `
  warningContainer.innerHTML += warningMessage
}

// Tear down warnings
function cleanWarnings(descriptionContainer, inputField) {
  descriptionContainer.style.display = "block"
  if (inputField.classList.contains("error-input")) {
    inputField.classList.remove("error-input")
  }
}

// Function to validate passwords
function validateFields(usernameInput, passwordInput, retypedPasswordInput) {
  let isValid = true
  let warningMessageContainer = document.querySelector(".warning-message")
  let descriptionContainer = document.querySelector(".instructions")
  let usernameInputField = document.querySelector("#input-username")
  let passwordInputField = document.querySelector("#input-password")
  let passwordReInputField = document.querySelector("#re-input-password")

  // Clean Any Previous Warnings
  cleanWarnings(descriptionContainer, usernameInputField)
  cleanWarnings(descriptionContainer, passwordInputField)
  cleanWarnings(descriptionContainer, passwordReInputField)
  warningMessageContainer.innerHTML = ""
  warningMessageContainer.style.display = "none"

  let isEmpty = false
  // Empty Fields
  if (usernameInput.length == 0) {
    isValid = false
    isEmpty = true
    setWarnings(descriptionContainer, warningMessageContainer, usernameInputField, "Username can't be empty.", "empty-username")
  }
  if (passwordInput.length == 0) {
    isValid = false
    isEmpty = true
    setWarnings(descriptionContainer, warningMessageContainer, passwordInputField, "Password can't be empty.", "empty-password")
  }
  if (retypedPasswordInput.length == 0) {
    isValid = false
    isEmpty = true
    setWarnings(descriptionContainer, warningMessageContainer, passwordReInputField, "Password confirmation can't be empty.", "empty-confirm-password")
  }
  if (isEmpty) {
    return isValid
  }
  // Don't Match
  if (passwordInput !== retypedPasswordInput) {
    setWarnings(descriptionContainer, warningMessageContainer, passwordReInputField, "Passwords must match.", "password-match")
    isValid = false
  }

  // Not long enough
  if (passwordInput.length < 8) {
    setWarnings(descriptionContainer, warningMessageContainer, passwordInputField, "Password not at least 8 characters.", "password-length")
    isValid = false
  }
  return isValid
}

document.querySelector("#btn-back").onclick = function(event) {
  event.preventDefault()
  console.log("CLICKED BACK")
  document.location.href = "../index.jsp"
}
