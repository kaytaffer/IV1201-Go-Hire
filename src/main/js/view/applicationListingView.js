import React from "react";
/**
 * Responsible for rendering an application listing.
 * @param props - props.
 * @param {Object} props.application - A single applicant.
 * @param {function} props.onHandleApplication - Called when clicking the 'handle' button.
 * @returns {JSX.Element} the rendered application listing.
 */
export function ApplicationListingView(props) {
    return(
        <tr>
            <td>{props.application.firstName}</td>
            <td>{props.application.lastName}</td>
            <td id={props.id + "-status"}>{props.application.status}</td>
            {props.application.status === "unhandled" && (
                <td> <button onClick={() => props.onHandleApplication(props.application)}>handle</button></td>
            )}
        </tr>
    )
}