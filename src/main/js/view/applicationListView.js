import React from "react";
import {ApplicationListingView} from "./applicationListingView";

/**
 * Responsible for rendering the list of applications.
 * @param props - props.
 * @param {Array} props.applications - list of application objects.
 * @param {Function} props.t - translation function for internationalization.
 * @param {function} props.onHandleApplication - Passed to each ApplicationListingView.
 * @returns {JSX.Element} the rendered application list.
 */
export function ApplicationListView(props) {
    return (
        <div>
            <h3>{props.t('applications')}</h3>
            <table>
                <thead>
                <tr>
                    <th>{props.t('first-name')}</th>
                    <th>{props.t('last-name')}</th>
                    <th>{props.t('status')}</th>
                </tr>
                </thead>
                <tbody>
                {props.applications.map((application, index) => (
                    <ApplicationListingView key={"application-listing-" + index} application={application}
                                            onHandleApplication={props.onHandleApplication} t={props.t}/>
                ))}
                </tbody>
            </table>
        </div>
    )
}