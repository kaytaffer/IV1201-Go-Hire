import React, {useState} from "react";
import {HomePageApplicantView} from "../view/homePageApplicantView";
import {HomePageRecruiterView} from "../view/homePageRecruiterView";
import {fetchListOfApplications} from "./api/apiCallHandler";
import {SERVER_INTERNAL} from "./api/errorMessages";
import {UserNoticeView} from "../view/userNoticeView";

/**
 * Responsible for the logic of the home page
 * @param props - props
 * @param {Object} props.user - the current logged-in user
 * @returns {JSX.Element} the rendered home page
 * @constructor
 */
export function HomePage(props){

    const [applications, setApplications] = useState(null)
    const [errorMessage, setErrorMessage] = useState("")

    function showApplications() {
        function resolveErrors(error) {
            if(error.errorType === SERVER_INTERNAL.errorType)
            setErrorMessage(SERVER_INTERNAL.message)
        }
        fetchListOfApplications().then(setApplications).catch(resolveErrors)
    }

    if(props.user.role === 'applicant')
        return errorMessage ? <UserNoticeView message={errorMessage} error={true}/> : <HomePageApplicantView user={props.user}/>;
    else if(props.user.role === 'recruiter') {
        return errorMessage ? <UserNoticeView message={errorMessage} error={true}/> : <HomePageRecruiterView
            user={props.user} applications={applications} onShowApplications={showApplications}/>
    }
}