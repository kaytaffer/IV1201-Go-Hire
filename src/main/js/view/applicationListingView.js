/**
 * Responsible for an application.
 * @param props - props
 * @param {Object} props.application - A single applicant
 * @returns {JSX.Element} the rendered recruiter home page
 */
export function ApplicationListingView(props) {
    return(
        <tr>
            <td>{props.firstName}</td>
            <td>{props.lastName}</td>
            <td>{props.status}</td>
        </tr>
    )}