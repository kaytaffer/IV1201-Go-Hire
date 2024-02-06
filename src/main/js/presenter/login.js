import React from "react";
import {LoginView} from "../view/loginView";

export function Login(props){

    function login(username, password) {
        // TODO
    }

    return <LoginView onLogin={login}/>
}