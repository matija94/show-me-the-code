import React from 'react';
import { Switch, Route } from 'react-router-dom';
import Template from '../components/Template';

const Switcher = () => {
    return (
        <Switch>
            <Route path="/template" component={Template} />
        </Switch>
    )
}

export default Switcher;