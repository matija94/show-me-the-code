import { combineReducers } from "redux";
import userReducer from "./userReducer";
import templateReducer from "./templateReducer";

const rootReducer = combineReducers({
    user: userReducer,
    template: templateReducer
});

export default rootReducer;