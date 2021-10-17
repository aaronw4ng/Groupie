export const validateUsername = () => {
    // Endpoint to check if username is already in DB
    return true
  }
  
  // Function to validate passwords
  export const validatePasswords = (passwordInput, retypedPasswordInput) => {
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