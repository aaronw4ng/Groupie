// Variables needed for helper functions
var usernameInputField = document.querySelector("#input-username")
var warningMessageContainer = document.querySelector(".warning-message")
var descriptionContainer = document.querySelector(".instructions")

// Clear inputs on page refresh
function clearFields() {
    document.getElementsByClassName("account-form")[0].reset()
}
window.addEventListener("load", clearFields, false);

// Handle on 'Create Account'
function handleRegisterClick(event) {
  event.preventDefault()
  const usernameInput = document.querySelector("#input-username").value
  const passwordInput = document.querySelector("#input-password").value
  const rePasswordInput = document.querySelector("#re-input-password").value

  if (validateFields(usernameInput, passwordInput, rePasswordInput)) {
    $.ajax({
      method: "POST",
      url: "../register",
      data: {
        username: usernameInput,
        password: passwordInput,
      },
      // Check if username taken
      success: function (result) {
        console.log(result)
        // if successful registration, then will return userID
        // otherwise, it will return -1
        if (result != "-1") {
          alert("Account successfully created!")
          // User session persistence
          if (!sessionStorage.getItem("username")) {
            sessionStorage.setItem("username", usernameInput)
          }
          if (!sessionStorage.getItem("userId")) {
            sessionStorage.setItem("userId", result)
          }
          document.location.href = "create-proposal.jsp"
        } else {
          setWarnings(usernameInputField, "Username has already been taken", "username-taken")
        }
      },
    })
  }
  return false
}

// Warning Message Set Up
function setWarnings(inputField, message, messageName) {
  descriptionContainer.style.display = "none"
  inputField.classList.add("error-input")
  warningMessageContainer.style.display = "block"
  let warningMessage = `
  <p name=${messageName}>${message}</p>
  `
  warningMessageContainer.innerHTML += warningMessage
}

// Tear down warnings
function cleanWarnings(inputField) {
  descriptionContainer.style.display = "block"
  if (inputField.classList.contains("error-input")) {
    inputField.classList.remove("error-input")
  }
}

// Function to validate passwords
function validateFields(usernameInput, passwordInput, retypedPasswordInput) {
  // querySelector returns null if empty -> use for validation
  let isValid = true
  let isEmpty = false

  let passwordInputField = document.querySelector("#input-password")
  let passwordReInputField = document.querySelector("#re-input-password")

  // Clean Any Previous Warnings
  cleanWarnings(usernameInputField)
  cleanWarnings(passwordInputField)
  cleanWarnings(passwordReInputField)
  warningMessageContainer.innerHTML = ""
  warningMessageContainer.style.display = "none"

  // Empty Fields
  if (!usernameInput || !passwordInput || !retypedPasswordInput) {
    isValid = false
    isEmpty = true
    if (!usernameInput) setWarnings(usernameInputField, "Username can't be empty.", "empty-username")
    if (!passwordInput) setWarnings(passwordInputField, "Password can't be empty.", "empty-password")
    if (!retypedPasswordInput) setWarnings(passwordReInputField, "Password confirmation can't be empty.", "empty-confirm-password")
  }
  if (isEmpty) return isValid

  // Don't Match
  if (passwordInput !== retypedPasswordInput) {
    setWarnings(passwordReInputField, "Passwords must match.", "password-match")
    isValid = false
  }
  // Not long enough
  if (passwordInput.length < 8) {
    setWarnings(passwordInputField, "Password not at least 8 characters.", "password-length")
    isValid = false
  }
  return isValid
}

document.querySelector("#btn-back").onclick = function(event) {
  event.preventDefault()
  console.log("CLICKED BACK")
  document.location.href = "../index.jsp"
}
