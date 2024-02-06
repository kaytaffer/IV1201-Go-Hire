import React from "react";
import {LoginView} from "../view/loginView";
import {authenticateLogin} from "./api/apiCallHandler";

export function Login(props){

    function login(username, password) {
       authenticateLogin(username, password).then(user => props.onLoggedIn(user))
        //TODO Handle promise error
    }

    return <LoginView onLogin={login}/>
}