import {USER_LOGIN, USER_LOGOUT} from '../actionTypes';

export const login = (token, userEmail) => ({
    type: USER_LOGIN,
    token,
    userEmail,
});

export const logout = () => ({
    type: USER_LOGOUT,
});
