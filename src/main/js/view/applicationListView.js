import {ApplicationListingView} from "./applicationListingView";

/**
 * Responsible for rendering the list of applications.
 * @param props - props
 * @param {Array} props.applications - list of application objects
 * @returns {JSX.Element} the rendered recruiter home page
 */
export function ApplicationListView(props) {
    return (
        <table>
            {props.map(application => <ApplicationListingView props={application}/>)}
        </table>
)}