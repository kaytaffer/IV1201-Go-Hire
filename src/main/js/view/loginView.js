import React from "react";

/**
 * Responsible for rendering the login page
 * @param props props
 * @param {function} props.onLogin - called when user submits login form
 * @returns {JSX.Element} the rendered login view
 * @constructor
 */
export function LoginView(props) {

    function loginACB(event) {
        event.preventDefault()
        let username = document.getElementById("login-form-username").value
        let password = document.getElementById("login-form-password").value
        props.onLogin(username, password)
    }

    return <div>
        <h1>Login</h1>
        <form onSubmit={loginACB}>
            <label>Username</label>
            <input id="login-form-username" type="text"/><br/>
            <label>Password</label>
            <input id="login-form-password" type="password"/><br/>
            <input type="submit" value="Submit" />
        </form>
    </div>
}