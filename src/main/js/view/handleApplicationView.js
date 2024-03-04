import React from "react";
import {UserNoticeView} from "./userNoticeView";

/**
 * Responsible for rendering a view in which to handle application status.
 * @param props - props.
 * @param {Object} props.application The application being handled.
 * @param {function} props.submitForm Called when the form is submitted.
 * @param {String} props.errorMessage Any error message to be displayed in child components.
 * @returns {JSX.Element} the rendered application handling component.
 */
export function HandleApplicationView(props) {

    function submission(event) {
        event.preventDefault()
        let newStatus = document.getElementById("handle-application-form-new-status").value
        let username = document.getElementById("handle-application-form-username").value
        let password = document.getElementById("handle-application-form-password").value
        props.submitForm(props.application.id, newStatus, username, password)
    }

    return (
        <div>
            <h4>Handle application for '{props.application.firstName} {props.application.lastName}'</h4>
            <p>Select the new status and verify your identity by entering username and password</p>

            <form onSubmit={submission}>
                <select id="handle-application-form-new-status">
                    <option value="">Unhandled</option>
                    <option value="accepted">Accept applicant</option>
                    <option value="rejected">Reject applicant</option>
                </select><br/>

                <label>Username</label>
                <input id="handle-application-form-username" type="text"/><br/>

                <label>Password</label>
                <input id="handle-application-form-password" type="password"/><br/>

                <input type="submit" value="Submit"/>
            </form>

            {props.errorMessage && <UserNoticeView message={props.errorMessage} error={true}/>}
        </div>
    )
}