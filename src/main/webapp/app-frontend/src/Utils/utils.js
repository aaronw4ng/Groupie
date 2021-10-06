// File with reusable functions

// Function to validate username
export const validateUsername = () => {
  // Endpoint to check if username is already in DB
  return true
}

// Function to validate passwords
export const validatePasswords = () => {
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

export function handleUsernameChange(value) {
  setUsername(prevUsername => (prevUsername = value))
}

export function handlePasswordChange(value) {
  setPassword(prevPass => (prevPass = value))
}

export function handleRetypedPasswordChange(value) {
  setRetypedPassword(prevRetypedPass => (prevRetypedPass = value))
}
