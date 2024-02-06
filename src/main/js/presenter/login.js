import React from "react";
import {LoginView} from "../view/loginView";
import {authenticateLogin} from "./api/apiCallHandler";

/**
 * Responsible for the logic of the login page
 * @param props props
 * @param {function} props.onLoggedIn - called when user successfully logs in
 * @returns {JSX.Element} the rendered login view
 * @constructor
 */
export function Login(props){

    function login(username, password) {
       authenticateLogin(username, password).then(user => props.onLoggedIn(user))
        //TODO Handle promise error
    }

    return <LoginView onLogin={login}/>
}