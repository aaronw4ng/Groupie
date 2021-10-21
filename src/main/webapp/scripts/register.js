function handleRegisterClick(event) {
  event.preventDefault()
  const usernameInput = document.querySelector("#input-username").value
  const passwordInput = document.querySelector("#input-password").value
  const rePasswordInput = document.querySelector("#re-input-password").value

  console.log(usernameInput + " " + passwordInput + " " + rePasswordInput)

  // Validate passwords

  console.log("Creating Account...")
  if (validatePasswords(passwordInput, rePasswordInput)) {
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

// Function to validate passwords
function validatePasswords(passwordInput, retypedPasswordInput) {
  // Don't Match
  if (passwordInput !== retypedPasswordInput) {
    alert("Passwords don't match! Please try again")
    return false
  }

  // Not long enough
  if (passwordInput.length < 8) {
    alert("Passwords not long enough! Please try again")
    return false
  }
  return true
}

document.querySelector("#btn-back").onclick = function(event) {
  event.preventDefault()
  console.log("CLICKED BACK")
  document.location.href = "../index.jsp"
}
