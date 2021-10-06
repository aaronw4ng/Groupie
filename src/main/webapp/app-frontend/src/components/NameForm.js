import React, { Component } from "react"

export default class NameForm extends React.Component {
  constructor(props) {
    super(props)
    this.state = { username: "", password: "", retypedPass: "" }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  handleChange(event) {
    this.setState({
      username: event.target.value,
      password: event.target.value,
      retypedPass: event.target.value,
    })
  }

  handleSubmit(event) {
    alert("Username: " + this.state.value)
    event.preventDefault()
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Username:
          <input
            type="text"
            value={this.state.username}
            onChange={this.handleChange}
          />
        </label>
        <label>
          Password:
          <input
            type="text"
            value={this.state.password}
            onChange={this.handleChange}
          />
        </label>
        <label>
          Re-type Password:
          <input
            type="text"
            value={this.state.retypedPass}
            onChange={this.handleChange}
          />
        </label>
        <input type="submit" value="Create Account" />
      </form>
    )
  }
}
