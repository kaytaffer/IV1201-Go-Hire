import React from "react";

/**
 * Responsible for rendering the login form.
 * @param props props.
 * @param {function} props.onLogin - called when user submits login form.
 * @param {Function} props.t - translation function for internationalization.
 * @returns {JSX.Element} the rendered login form.
 * @constructor
 */
export function LoginView(props) {

    function loginACB(event) {
        event.preventDefault()
        let username = document.getElementById("login-form-username").value
        let password = document.getElementById("login-form-password").value
        props.onLogin(username, password)
    }

    return (
        <div>
            <h1>{props.t('login')}</h1>
            <form onSubmit={loginACB}>
                <label>{props.t('username')}</label>
                <input id="login-form-username" type="text"/><br/>
                <label>{props.t('password')}</label>
                <input id="login-form-password" type="password"/><br/>
                <input type="submit" value={props.t('submit')}/>
            </form>
        </div>
    )
}