import React from "react";
import {UserNoticeView} from "./userNoticeView";

/**
 * Responsible for rendering a view in which to handle application status.
 * @param props - props.
 * @param {Object} props.application The application being handled.
 * @param {function} props.submitForm Called when the form is submitted.
 * @param {String} props.errorMessage Any error message to be displayed in child components.
 * @param {Function} props.t - translation function for internationalization.
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
        <div id="single-application">
            <h4>{props.t('handle-application-for')} '{props.application.firstName} {props.application.lastName}'</h4>
            <p>{props.t('handle-application-description')}</p>

            <form onSubmit={submission}>
                <select id="handle-application-form-new-status">
                    <option value="">{props.t('unhandled')}</option>
                    <option value="accepted">{props.t('accept-applicant')}</option>
                    <option value="rejected">{props.t('reject-applicant')}</option>
                </select><br/>

                <label>{props.t('username')}</label>
                <input id="handle-application-form-username" type="text"/><br/>

                <label>{props.t('password')}</label>
                <input id="handle-application-form-password" type="password"/><br/>

                <input id="handle-application-form-submit" type="submit" value={props.t('submit')}/>
            </form>

            {props.errorMessage && <UserNoticeView message={props.errorMessage} error={true}/>}
        </div>
    )
}