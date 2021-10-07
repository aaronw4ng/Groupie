import React, {useState} from "react";
import InputField from "../InputField"
import Button from "../Button";

function LoginPage() {

  const [usernameInput, setUsername] = useState("")
  const [passwordInput, setPassword] = useState("")

  function handleLogin() {
    console.log("Logging In")
    console.log("Username: " + usernameInput)
    console.log("Password: " + passwordInput)
    
    // TODO: Send POST request to Elizabeth's servlet
      /*
      // POST request using fetch()
      fetch("/greeting", {

          // Adding method type
          method: "POST",

          // Adding body or contents to send
          body: JSON.stringify({
              username: usernameInput,
              password: passwordInput
          }),

          // Adding headers to the request
          headers: {
              "Content-type": "application/json; charset=UTF-8"
          }
      }).then(response => response.text()).then(result => {console.log(result);})

          // Converting to JSON
          .then(response => response.json())

          // Displaying results to console
          .then(json => console.log(json));
       */
    /*
      const servletURL = "/LoginServlet"
     const postBody = {
       username: usernameInput,
       password: passwordInput
     }
     const requestMetadata = {
       method: "POST",
       headers: {
         "Content-Type": "application/json"
       },
       body: JSON.stringify(postBody)

     }

     fetch(servletURL, requestMetadata)
*/

  }

  function handleUsernameChange(value) {
    setUsername(prevUsername => prevUsername = value)
  }

  function handlePasswordChange(value) {
    setPassword(prevPass => prevPass = value)
  }

  return (
    <div>
      <h1>Login</h1>
      <InputField 
        data={{
          type: "text", 
          label: "Username", 
          setState: handleUsernameChange, 
          cssClass: "field-input",
          name:  "input-username"
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
      <Button 
        data={{
          innerText : "LOGIN", 
          onClick: handleLogin,
          name: "btn-login"
        }}
      />
    </div>
  );
}

export default LoginPage;
