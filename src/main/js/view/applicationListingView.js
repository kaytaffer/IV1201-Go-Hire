import React from "react";
/**
 * Responsible for an application.
 * @param props - props
 * @param {Object} props.application - A single applicant
 * @param {function} props.onHandleApplication - Called when clicking the 'handle' button.
 * @returns {JSX.Element} the rendered recruiter home page
 */
export function ApplicationListingView(props) {
    return(
        <tr>
            <td>{props.application.firstName}</td>
            <td>{props.application.lastName}</td>
            <td>{props.application.status}</td>
            <td><button onClick={() => props.onHandleApplication(props.application)}>handle</button></td>
        </tr>
    )}