import React from "react";

/**
 * Responsible for rendering the create new applicant form.
 * @param props props
 * @param {function} props.onCreate - called when user submits new applicant form
 * @returns {JSX.Element} the rendered new applicant view
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

    return <div>
        <h1>Create new Applicant</h1>
        <form onSubmit={submit}>
            <label>First Name</label>
            <input id="create-applicant-form-first-name" type="text" placeholder="max 255 characters"/><br/>
            <label>Last Name</label>
            <input id="create-applicant-form-last-name" type="text" placeholder="max 255 characters"/><br/>
            <label>Email</label>
            <input id="create-applicant-form-email" type="email" placeholder="example@email.com"/><br/>
            <label>Person Number</label>
            <input id="create-applicant-form-person-number" type="text" placeholder="YYYYMMDD-XXXX"/><br/>
            <label>Username</label>
            <input id="create-applicant-form-username" type="text" placeholder="max 255 characters"/><br/>
            <label>Password</label>
            <input id="create-applicant-form-password" type="password" placeholder="max 255 characters"/><br/>
            <input type="submit" value="Submit"/>
        </form>
    </div>

}
