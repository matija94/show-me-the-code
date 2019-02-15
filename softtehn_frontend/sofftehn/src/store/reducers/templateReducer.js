import { TEMPLATE_CREATE } from "../actionTypes";

const initState = {};

const templateReducer = (state = initState, action) => {

    switch (action.type) {
        case TEMPLATE_CREATE:
            return {
              ...state  
            };
    
        default:
            return state;
    }

}

export default templateReducer;