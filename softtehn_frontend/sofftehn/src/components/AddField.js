import React, { Component } from 'react';
import Field from './Field';

class AddField extends Component {

    state = {
        type: null
    };

    inputType = (e) => {
        this.setState({
            type: e.target.value
        });
    };

    pickFieldType = () => {
        switch (this.state.type) {
            case 'STRING':
                return <Field type={'text'} />;
            case 'NUMBER':
                return <Field type={'number'} />;
            case 'DATE':
                return <Field type={'date'} />
            default:
                return null;
        }
    };

    render() {
        return (
        <div className="row">
            <div className="input-field col s4">
                <input id="fieldName" name="fieldName" type="text" className="validate" />
                <label htmlFor="fieldName">Field name</label>
            </div>
            <div className="input-field col s4">
                <select name="fieldType" onChange={this.inputType}>
                    <option value="" defaultChecked>Choose field type</option>
                    <option value="STRING">String</option>
                    <option value="NUMBER">Number</option>
                    <option value="DATE">Date</option>
                </select>
            </div>
            { this.state.type ? this.pickFieldType() : null }
        </div>
        )
    }
}

export default AddField
