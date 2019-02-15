import React, { Component } from 'react';
import Navigation from './components/Navigation';
import Switcher from './components/Switcher';

class App extends Component {
  render() {
    return (
      <div className="app-container container">
        <Navigation />
        <Switcher />
      </div>
    );
  }
}

export default App;
