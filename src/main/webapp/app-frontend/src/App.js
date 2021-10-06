import React, { Component } from "react"
import "./styles/App.css"
import AccountCreation from "./components/AccountCreation"
import LoginPage from "./components/LoginPage"

import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom"
function App() {
  return (
    <Router>
      <div className="App">
        <Switch>
          <Route exact path="/">
            <Redirect to="/accountcreation" />
          </Route>
          <Route exact path="/simple-reactjs-app">
            <Redirect to="/accountcreation" />
          </Route>
          <Route path="/accountcreation" component={AccountCreation} />
          <Route path="/loginpage" component={LoginPage} />
        </Switch>
      </div>
    </Router>
  )
}

export default App
