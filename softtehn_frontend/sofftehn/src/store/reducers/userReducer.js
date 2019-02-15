import { USER_LOGIN, USER_LOGOUT } from "../actionTypes";

const initState = {};

const userReducer = (state = initState, action) => {

    switch (action.type) {
        case USER_LOGIN:
            return {
                ...state
            };
    
        case USER_LOGOUT:
            return {
                ...state
            };

        default:
            return state;
    }

}

export default userReducer;