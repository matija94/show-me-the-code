import React from 'react'

const Field = ({ type }) => {
    return (
      <div className="input-field col s4">
        <input placeholder="Field value" id="fieldValue" name="fieldValue" type={type} className="validate" />
      </div>
    )
}

export default Field
