import React from "react";
import {ApplicationListView} from "./applicationListView";

/**
 * Responsible for rendering the home page for recruiter.
 * @param props - props.
 * @param {Object} props.user - the current logged-in user.
 * @param {Array} props.applications - list of application objects.
 * @param {function} props.onShowApplications - called when user clicks the showApplications button.
 * @param {function} props.onHandleApplication - Passed to each ApplicationListingView.
 * @param {Function} props.t - translation function for internationalization.
 * @returns {JSX.Element} the rendered recruiter home page.
 */
export function HomePageRecruiterView(props) {
    return (
        <div>
            <h1>{props.t('recruiter')}</h1>
            {props.applications && (
                <ApplicationListView applications={props.applications}
                                     onHandleApplication={props.onHandleApplication}
                                     t={props.t}/>
            )}
            <p><button id={"showApplications"} onClick={props.onShowApplications}>
                {props.t('display-applications')}
            </button></p>
        </div>
    )
}