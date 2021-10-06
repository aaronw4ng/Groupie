import React, { Component } from 'react';
import './styles/App.css';
import AccountCreation from './components/AccountCreation';
import { BrowserRouter as Router, Switch, Route, Redirect} from 'react-router-dom';
class App extends Component {
  render() {
    console.log("Host URL"+process.env.PUBLIC_URL);
    return (

      <Router basename={process.env.PUBLIC_URL}>
        <div className="App">
        <header className="App-header">
          <h1 className="App-title">Scheduling App</h1>
        </header>
        <AccountCreation/>
          {/* <Switch>
                <Route exact path= "/" render={() => (
                  <Redirect to="/customerlist"/>
                )}/>
                 <Route exact path='/customerlist' component={Customers} />
          </Switch> */}
      </div>
    </Router>
    );
  }
}

export default App;
