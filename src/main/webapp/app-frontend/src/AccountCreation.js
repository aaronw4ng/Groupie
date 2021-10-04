import React, { Component } from "react"
import Panel from "react-bootstrap/lib/Panel"
import Button from "react-bootstrap/lib/Button"
import axios from "axios"
import NameForm from "./NameForm"

export default class AccountCreation extends Component {
  constructor(props) {
    super(props)
    this.state = {}
  }

  // Function which is called the first time the component loads
  componentDidMount() {}

  render() {
    return <div><NameForm/>{<Button>Go Back</Button>}</div>
  }
}
