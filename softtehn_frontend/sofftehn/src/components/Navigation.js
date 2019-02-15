import React from 'react';
import { NavLink } from 'react-router-dom';

const Navigation = () => {

    return (
        <nav>
            <div className="nav-wrapper">
                <NavLink className="brand-logo" exact to="/">Logo</NavLink>
                <ul id="nav-mobile" className="right hide-on-med-and-down">
                    <li>
                        <NavLink exact to="/">Home</NavLink>
                    </li>
                    <li>
                        <NavLink to="/template">Template</NavLink>
                    </li>
                </ul>
            </div>
        </nav>
    )

}

export default Navigation;