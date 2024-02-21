import React, {useState} from "react";
import {LoginView} from "../view/loginView";
import {authenticateLogin, createNewApplicant} from "./api/apiCallHandler";
import {CreateNewApplicantView} from "../view/createNewApplicantView";
import {LOGIN_FAIL, USER_INPUT_ERROR, USERNAME_ALREADY_EXISTS} from "./api/errorMessages";
import {UserNoticeView} from "../view/userNoticeView";

/**
 * Responsible for the logic of the login page
 * @param props props
 * @param {function} props.onLoggedIn - called when user successfully logs in
 * @returns {JSX.Element} the rendered login view
 * @constructor
 */
export function Login(props){

    const [newUserIsCreated, setNewUserIsCreated] = useState(false)
    const [errorMessage, setErrorMessage] = useState(null)

    function catchPromiseError(error) {
        if (error.message === LOGIN_FAIL.errorType)
            setErrorMessage(LOGIN_FAIL.message)
        else if (error.message === USER_INPUT_ERROR.errorType)
            setErrorMessage(USER_INPUT_ERROR.message)
        else if (error.message === USERNAME_ALREADY_EXISTS.errorType) {
            setErrorMessage(USERNAME_ALREADY_EXISTS.message) }
    }

    function login(username, password) {
        authenticateLogin(username, password).then(user => props.onLoggedIn(user))
            .catch(catchPromiseError)
    }

    function newApplicant(firstName, lastName, email, personNumber, username, password) {
        createNewApplicant(firstName, lastName, email, personNumber, username, password)
            .then(user => {if(user) setNewUserIsCreated(true)}).catch(catchPromiseError)
    }

    return (
        <div>
            <LoginView onLogin={login}/>
            {newUserIsCreated ? <UserNoticeView message={"Account successfully created"} error={false}/> :
                            <CreateNewApplicantView onCreate={newApplicant}/>}
            {errorMessage && <UserNoticeView message={errorMessage} error={true}/>}
        </div>
    )
}