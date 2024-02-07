import React from "react";

/**
 * Responsible for rendering the home page for recruiter
 * @param props - props
 * @param {Object} props.user - the current logged-in user
 * @returns {JSX.Element} the rendered recruiter home page
 * @constructor
 */
export function HomePageRecruiterView(props) {
    return (
        <div>
            <h1>Recruiter</h1>
            <p>Welcome {props.user.username}</p>
        </div>
    )
}