import { createStore } from "redux";
import rootReducer from "./ListReducer";


const store = createStore(rootReducer);

export default store;
