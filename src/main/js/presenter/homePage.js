import React from "react";
import {HomePageApplicantView} from "../view/homePageApplicantView";
import {HomePageRecruiterView} from "../view/homePageRecruiterView";

/**
 * Responsible for the logic of the home page
 * @param props - props
 * @param {Object} props.user - the current logged-in user
 * @returns {JSX.Element} the rendered home page
 * @constructor
 */
export function HomePage(props){

    if(props.user.role === 'applicant')
        return <HomePageApplicantView user={props.user}/>;
    else if(props.user.role === 'recruiter')
        return <HomePageRecruiterView user={props.user}/>
    else
        return <div>error</div> // TODO extend error message

}