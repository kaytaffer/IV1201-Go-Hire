import React from "react";
/**
 * Responsible for rendering an application listing.
 * @param props - props.
 * @param {Object} props.application - A single applicant.
 * @param {function} props.onHandleApplication - Called when clicking the 'handle' button.
 * @param {Function} props.t - translation function for internationalization.
 * @returns {JSX.Element} the rendered application listing.
 */
export function ApplicationListingView(props) {
    return(
        <tr>
            <td>{props.t(props.application.firstName)}</td>
            <td>{props.t(props.application.lastName)}</td>
            <td id={props.id + "-status"}>{props.t(props.application.status)}</td>
            {props.application.status === "unhandled" && (
                <td>
                    <button id={props.id + "-button"} onClick={() => props.onHandleApplication(props.application)}>
                        {props.t('handle')}
                    </button>
                </td>
            )}
        </tr>
    )
}