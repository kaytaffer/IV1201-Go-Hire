import React, {useState} from "react";
import {LoginView} from "../view/loginView";
import {authenticateLogin, createNewApplicant} from "./api/apiCallHandler";
import {CreateNewApplicantView} from "../view/createNewApplicantView";
import {
    LOGIN_FAIL,
    SERVER_INTERNAL,
    USER_INPUT_ERROR,
    USERNAME_ALREADY_EXISTS
} from "./api/errorMessages";
import {UserNoticeView} from "../view/userNoticeView";

/**
 * Responsible for the logic of the login page
 * @param props props
 * @param {function} props.onLoggedIn - called when user successfully logs in
 * @returns {JSX.Element} the rendered login view
 */
export function Login(props){

    const [newUserIsCreated, setNewUserIsCreated] = useState(false)
    const [displayMessage, setDisplayMessage] = useState("")

    const POSSIBLE_LOGIN_ERRORS = [LOGIN_FAIL, USER_INPUT_ERROR, USERNAME_ALREADY_EXISTS, SERVER_INTERNAL]

    function catchPromiseError(error) {
        function checkErrorType(possibleError) {
            return error.message === possibleError.errorType
        }
        setDisplayMessage(POSSIBLE_LOGIN_ERRORS.find(checkErrorType).message)
    }

    function login(username, password) {
        authenticateLogin(username, password).then(user => props.onLoggedIn(user))
            .catch(catchPromiseError)
    }

    function newApplicant(firstName, lastName, email, personNumber, username, password) {
        createNewApplicant(firstName, lastName, email, personNumber, username, password)
            .then(user => {if(user) {
                setDisplayMessage("Account successfully created");
                setNewUserIsCreated(true);
            }
            }).catch(catchPromiseError)
    }

    return (
        <div>
            {displayMessage && <UserNoticeView message={displayMessage}/>}
            <LoginView onLogin={login}/>
            {!newUserIsCreated && <CreateNewApplicantView onCreate={newApplicant}/>}

        </div>
    )
}