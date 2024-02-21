import React from "react";
/**
 * Responsible for an application.
 * @param props - props
 * @param {Object} props.application - A single applicant
 * @returns {JSX.Element} the rendered recruiter home page
 */
export function ApplicationListingView(props) {
    return(
        <tr>
            <td>{props.application.firstName}</td>
            <td>{props.application.lastName}</td>
            <td>{props.application.status}</td>
        </tr>
    )}