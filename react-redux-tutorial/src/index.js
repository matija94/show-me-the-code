import React from "react";
import { render } from "react-dom";
import { Provider } from "react-redux";
import store from "./js/Store";
import App from "./js/components/App";

render(
    <Provider store={store}>
      <App />
    </Provider>
    ,
    document.getElementById("root")
);