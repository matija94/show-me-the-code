import React from 'react';
import { connect } from 'react-redux';
import {GOOGLE_CLIENT_ID} from "../constants";
import { login, logout } from "../store/actions/user";
import { GoogleLogin } from "react-google-login";
/* components */
import Navigation from '../components/Navigation';
/* styles */
import './base.css';

class BaseLayout extends React.PureComponent {

    constructor() {
        super();

        this.login = this.login.bind(this);
        this.logout = this.logout.bind(this);
    }

    onFailure(error){
        console.log("onFailure() ", error);
    };

    login(response) {
        this.props.login(response.tokenObj, response.profileObj.email);
    };


    logout() {
        this.props.logout();
    }

    render() {
        return (
            <div className="base-layout">
                {!this.props.user ?
                    <div className="login">
                        <GoogleLogin
                            className="btn btn-primary btn-sx"
                            clientId={GOOGLE_CLIENT_ID}
                            onSuccess={this.login}
                            onFailure={this.onFailure}
                            theme="dark"
                        />
                    </div>
                    :
                    <div className="base-layout-wrapper">
                        <Navigation
                            logout={this.logout}
                            user={this.props.user}
                        />
                        <div className="base-layout-content">
                            {this.props.children}
                        </div>
                    </div>
                }
            </div>
        )
    }
}

const mapStateToProps = state => ({
    //isLoading: state.appReducer.isLoading,
    user: state.user.user,
});

const mapDispatchToProps = dispatch => ({
    login: (token, userEmail) => dispatch(login(token, userEmail)),
    logout: () => dispatch(logout()),
});

export default connect(mapStateToProps, mapDispatchToProps)(BaseLayout);
