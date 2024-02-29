import React from "react";

/**
 * Responsible for rendering a view in which to handle application status.
 * @param props - props
 * @param {Object} props.application The application being handled.
 * @param {function} props.submitForm Called when the form is submitted.
 * @returns {JSX.Element} the rendered application handling component.
 */
export function HandleApplicationView(props) {
    return (<div>
        <h4>Handle application for '{props.application.firstName} {props.application.lastName}</h4>
        <p>Select the new status and verify your identity by entering username and password</p>

        <form onSubmit={props.submitForm}>
            <select id="new-status">
                <option value="">Unhandled</option>
                <option value="accept">Accept applicant</option>
                <option value="reject">Reject applicant</option>
            </select><br/>
            <label>Username</label>
            <input id="handle-application-form-username" type="text"/><br/>
            <label>Password</label>
            <input id="handle-application-form-password" type="password"/><br/>
            <input type="submit" value="Submit"/>
        </form>
    </div>)
}