import React, { useState } from "react"
import InputField from "../InputField"
import Button from "../Button"
import { useHistory } from "react-router-dom"
import {
  validatePasswords,
  validateUsername,
  handleUsernameChange,
  handlePasswordChange,
  handleRetypedPasswordChange,
} from "../../Utils/utils.js"

function AccountCreation() {
  const [usernameInput, setUsername] = useState("")
  const [passwordInput, setPassword] = useState("")
  const [retypedPasswordInput, setRetypedPassword] = useState("")
  const history = useHistory()

  // Util functions need to be present in AccountCreation since they need access to state Setter functions
  function handleUsernameChange(value) {
    setUsername(prevUsername => (prevUsername = value))
  }
  
  function handlePasswordChange(value) {
    setPassword(prevPass => (prevPass = value))
  }


  function handleRetypedPasswordChange(value) {
    setRetypedPassword(prevRetypedPass => (prevRetypedPass = value))
  }

  function handleCreation() {
    console.log(passwordInput + retypedPasswordInput)
    // Validate passwords
    if (validatePasswords(passwordInput, retypedPasswordInput) && validateUsername()) {
      alert("Creating account...")
      // TODO: Send POST request to Elizabeth's servlet

      // const servletURL = ""
      // const postBody = {
      //   username: usernameInput,
      //   password: passwordInput
      // }
      // const requestMetadata = {
      //   method: "POST",
      //   headers: {
      //     "Content-Type": "application/json"
      //   },
      //   body: JSON.stringify(postBody)

      // }

      // fetch(servletURL, requestMetadata)

      // After posting, information should be in DB
      // Route to the account creation page
      history.push("/loginpage")
    }
  }

  return (
    <div>
      <h1>Create Account</h1>
      <h2>Create an account to get started with the scheduling app!</h2>
      <InputField
        data={{
          type: "text",
          label: "Username",
          setState: handleUsernameChange,
          cssClass: "field-input",
          name: "input-username"
        }}
      />
      <InputField
        data={{
          type: "password",
          label: "Password",
          setState: handlePasswordChange,
          cssClass: "field-input",
          name: "input-password"
        }}
      />
      <InputField
        data={{
          type: "password",
          label: "Re-type Password",
          setState: handleRetypedPasswordChange,
          cssClass: "field-input",
          name: "input-retype-password"
        }}
      />
      <Button 
        data={{ 
          innerText: "Create Account", 
          onClick: handleCreation,
          name: "btn-create-account" 
        }} 
      />
    </div>
  )
}

export default AccountCreation
