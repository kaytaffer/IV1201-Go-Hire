import React from "react";

/**
 * Responsible for rendering the home page for applicants.
 * @param props - props.
 * @param {Object} props.user - the current logged-in user.
 * @returns {JSX.Element} the rendered applicant home page.
 * @constructor
 */
export function HomePageApplicantView(props) {
    return (
        <div>
            <h1>Applicant</h1>
            <p>Welcome {props.user.username}</p>
        </div>
    )
}