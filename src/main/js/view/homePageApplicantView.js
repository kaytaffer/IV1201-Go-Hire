import React from "react";

/**
 * Responsible for rendering the home page for applicants.
 * @param props - props.
 * @param {Function} props.t - translation function for internationalization.
 * @param {Object} props.user - the current logged-in user.
 * @returns {JSX.Element} the rendered applicant home page.
 * @constructor
 */
export function HomePageApplicantView(props) {
    return (
        <div>
            <h1>{props.t('applicant')}</h1>
            <p>{props.t('welcome')} {props.user.username}</p>
        </div>
    )
}