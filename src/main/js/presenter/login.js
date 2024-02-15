import React, {useState} from "react";
import {LoginView} from "../view/loginView";
import {authenticateLogin, createNewApplicant} from "./api/apiCallHandler";
import {CreateNewApplicantView} from "../view/createNewApplicantView";

/**
 * Responsible for the logic of the login page
 * @param props props
 * @param {function} props.onLoggedIn - called when user successfully logs in
 * @returns {JSX.Element} the rendered login view
 * @constructor
 */
export function Login(props){

    const [newUserIsCreated, setNewUserIsCreated] = useState(false)

    function catchPromiseError(error) {
        //TODO Handle promise error
    }

    function login(username, password) {
        authenticateLogin(username, password).then(user => props.onLoggedIn(user))
            .catch(catchPromiseError)

    }

    function newApplicant(firstName, lastName, email, personNumber, username, password) {
        createNewApplicant(firstName, lastName, email, personNumber, username, password)
            .then(user => {if(user) setNewUserIsCreated(true)}).catch(catchPromiseError)

    }

    return (<div>
        <LoginView onLogin={login}/>
        {newUserIsCreated ? <div>Account successfully created</div> : <CreateNewApplicantView onCreate={newApplicant}/>}
    </div>
    )
}