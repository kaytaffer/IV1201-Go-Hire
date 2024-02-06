import React from "react";

/**
 * Responsible for the logic of the home page
 * @param props - props
 * @param {Object} props.user - the current logged-in user
 * @returns {JSX.Element} the rendered home page
 * @constructor
 */
export function HomePage(props){

    //TODO add a homePageView, check role and render accordingly.
    return <div>
        <h1> Home Page</h1>
        <p> Welcome {props.user.username}, with role {props.user.role}</p>
        </div>;

}