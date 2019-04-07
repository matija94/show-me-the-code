import React, { Component } from 'react'
import AddField from './AddField'

class Template extends Component {

  state = {
    name: null,
    templateDesc: null,
    active: false
  };

  onChange = (e) => {
    this.setState({
      [e.target.id]: e.target.value
    });
  };

  expandField = (e) => {
    this.setState({
      active: true
    });
  };

  render() {    
    return (
      <div>
        <div className="row">
          <form className="col s12">
            <div className="row">
              <div className="input-field col s5">
                <input id="templateName" name="templateName" type="text" className="validate" onChange={this.onChange} />
                <label htmlFor="templateName">Template name</label>
              </div>
              <div className="input-field col s5">
                <input id="templateDesc" name="templateDesc" type="text" className="validate" onChange={this.onChange} />
                <label htmlFor="templateDesc">Description</label>
              </div>
              <div className="input-field col s2">
                <button className="btn waves-effect waves-light" type="button" onClick={this.expandField}>
                  Add field
                </button>
              </div>
            </div>
            {this.state.active ? <AddField /> : null}
          </form>
        </div>        
      </div>
    )
  }
}

export default Template
