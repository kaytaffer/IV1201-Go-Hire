import React from "react";

/**
 * Responsible for rendering the create new applicant form.
 * @param props props.
 * @param {function} props.onCreate - called when user submits new applicant form.
 * @param {Function} props.t - translation function for internationalization.
 * @returns {JSX.Element} the rendered new applicant form.
 */
export function CreateNewApplicantView(props) {

    function submit(event) {
        event.preventDefault()
        let firstName = document.getElementById("create-applicant-form-first-name").value
        let lastName = document.getElementById("create-applicant-form-last-name").value
        let email = document.getElementById("create-applicant-form-email").value
        let personNumber = document.getElementById("create-applicant-form-person-number").value
        let username = document.getElementById("create-applicant-form-username").value
        let password = document.getElementById("create-applicant-form-password").value
        props.onCreate(firstName, lastName, email, personNumber, username, password)
    }

    return (
        <div>
            <h1>{props.t('create-new-applicant')}</h1>
            <form onSubmit={submit}>
                <label>{props.t('first-name')}</label>
                <input id="create-applicant-form-first-name" type="text" placeholder={props.t('max-255-chars')}/><br/>

                <label>{props.t('last-name')}</label>
                <input id="create-applicant-form-last-name" type="text" placeholder={props.t('max-255-chars')}/><br/>

                <label>{props.t('email')}</label>
                <input id="create-applicant-form-email" type="email" placeholder="example@email.com"/><br/>

                <label>{props.t('person-number')}</label>
                <input id="create-applicant-form-person-number" type="text" placeholder="YYYYMMDD-XXXX"/><br/>

                <label>{props.t('username')}</label>
                <input id="create-applicant-form-username" type="text" placeholder={props.t('max-255-chars')}/><br/>

                <label>{props.t('password')}</label>
                <input id="create-applicant-form-password" type="password" placeholder={props.t('max-255-chars')}/><br/>

                <input id="create-applicant-form-submit" type="submit" value={props.t('submit')}/>
            </form>
        </div>
    )
}
