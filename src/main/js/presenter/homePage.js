import React, {useState} from "react";
import {HomePageApplicantView} from "../view/homePageApplicantView";
import {HomePageRecruiterView} from "../view/homePageRecruiterView";
import {fetchListOfApplications} from "./api/apiCallHandler";
import {
    INSUFFICIENT_CREDENTIALS,
    PAGE_DOES_NOT_EXIST,
    SERVER_INTERNAL,
} from "./api/errorMessages";
import {UserNoticeView} from "../view/userNoticeView";
import {PopupView} from "../view/popupView";
import {HandleApplicationView} from "../view/handleApplicationView";

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
    const [showSingleApplicant, setShowSingleApplicant] = useState(null)

        const POSSIBLE_FETCH_APPLICATION_ERRORS = [PAGE_DOES_NOT_EXIST, SERVER_INTERNAL, INSUFFICIENT_CREDENTIALS]

    function showApplications() {
        function resolveErrors(error) {
            function checkErrorType(possibleError) {
                return error.message === possibleError.errorType
            }
            setErrorMessage(POSSIBLE_FETCH_APPLICATION_ERRORS.find(checkErrorType).message)
        }
        fetchListOfApplications().then(setApplications).catch(resolveErrors)
    }

    function handleApplication(applicant) {
        setShowSingleApplicant(applicant)
    }

    function changeStatus(request) {

    }

    if(props.user.role === 'applicant')
        return errorMessage ? <UserNoticeView message={errorMessage} error={true}/> : <HomePageApplicantView user={props.user}/>;
    else if(props.user.role === 'recruiter') {
        return (<div> {
            errorMessage ? <UserNoticeView message={errorMessage} error={true}/> : <HomePageRecruiterView
            user={props.user} applications={applications} onShowApplications={showApplications} onHandleApplication={handleApplication}/>}

            <PopupView open={showSingleApplicant} onClose={() => setShowSingleApplicant(null)}>
                <HandleApplicationView application={showSingleApplicant} submitForm={changeStatus}/>
            </PopupView>
        </div>)
    }
}