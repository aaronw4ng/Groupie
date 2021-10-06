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

  function handleCreation() {
    // Validate passwords
    if (validatePasswords() && validateUsername()) {
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
        }}
      />
      <InputField
        data={{
          type: "password",
          label: "Password",
          setState: handlePasswordChange,
          cssClass: "field-input",
        }}
      />
      <InputField
        data={{
          type: "password",
          label: "Re-type Password",
          setState: handleRetypedPasswordChange,
          cssClass: "field-input",
        }}
      />
      <Button data={{ innerText: "Create Account", onClick: handleCreation }} />
    </div>
  )
}

export default AccountCreation
