import React from "react";
import {ApplicationListingView} from "./applicationListingView";

/**
 * Responsible for rendering the list of applications.
 * @param props - props
 * @param {Array} props.applications - list of application objects
 * @param {function} props.onHandleApplication - Passed to each ApplicationListingView.
 * @returns {JSX.Element} the rendered application list.
 */
export function ApplicationListView(props) {
    return (
        <table>
            <thead>
            <h3>Applications</h3>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            {props.applications.map((application, index) => (
                <ApplicationListingView key={"application-listing-" + index} application={application} onHandleApplication={props.onHandleApplication}/>
            ))}
            </tbody>
        </table>
)}