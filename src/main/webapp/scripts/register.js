function handleRegisterClick(event) {
  event.preventDefault()
  const usernameInput = document.querySelector("#input-username").value
  const passwordInput = document.querySelector("#input-password").value
  const rePasswordInput = document.querySelector("#re-input-password").value

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
          alert("SUCCESS")
        } else {
          alert("FAILED")
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
function setWarnings(descriptionContainer, warningContainer, inputField, message) {
  descriptionContainer.style.display = "none"
  inputField.classList.add("error-input")
  warningContainer.style.display = "block"
  let warningMessage = `
  <p>${message}</p>
  `
  warningContainer.innerHTML = warningMessage
}

// Tear down warnings
function cleanWarnings(descriptionContainer, warningContainer, inputField) {
  descriptionContainer.style.display = "block"
  if (inputField.classList.contains("error-input")) {
    inputField.classList.remove("error-input")
  }
  warningContainer.style.display = "none"
}

// Function to validate passwords
function validateFields(usernameInput, passwordInput, retypedPasswordInput) {
  let isValid = true
  let warningMessageContainer = document.querySelectorAll(".warning-message")
  let descriptionContainer = document.querySelector(".instructions")
  let passwordInputField = document.querySelector("#input-password")
  let passwordReInputField = document.querySelector("#re-input-password")

  // Clean Any Previous Warnings
  cleanWarnings(descriptionContainer, warningMessageContainer[0], passwordInputField)
  cleanWarnings(descriptionContainer, warningMessageContainer[1], passwordReInputField)

  // Empty Fields
  if (usernameInput.length == 0 || passwordInput.length == 0 || retypedPasswordInput.length == 0) {
    alert("Fields cannot be empty")
  }
  // Don't Match
  if (passwordInput !== retypedPasswordInput) {
    setWarnings(descriptionContainer, warningMessageContainer[1], passwordReInputField, "Passwords must match")
    isValid = false
  }

  // Not long enough
  if (passwordInput.length < 8) {
    setWarnings(descriptionContainer, warningMessageContainer[0], passwordInputField, "Password not at least 8 characters")
    isValid = false
  }
  return isValid
}

document.querySelector("#btn-back").onclick = function(event) {
  event.preventDefault()
  console.log("CLICKED BACK")
  document.location.href = "../index.jsp"
}
