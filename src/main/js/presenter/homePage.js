import React, {useState} from "react";
import {HomePageApplicantView} from "../view/homePageApplicantView";
import {HomePageRecruiterView} from "../view/homePageRecruiterView";
import {changeApplicationStatus, fetchListOfApplications} from "./api/apiCallHandler";
import {
    APPLICATION_ALREADY_HANDLED,
    INSUFFICIENT_CREDENTIALS, LOGIN_FAIL,
    PAGE_DOES_NOT_EXIST,
    SERVER_INTERNAL, AUTHENTICATION_FAIL,
    USER_INPUT_ERROR,
} from "./api/errorMessages";
import {UserNoticeView} from "../view/userNoticeView";
import {PopupView} from "../view/popupView";
import {HandleApplicationView} from "../view/handleApplicationView";
import {useTranslation} from "react-i18next";

/**
 * Responsible for the logic of the home page.
 * @param props - props.
 * @param {Object} props.user - the current logged-in user.
 * @returns {JSX.Element} the rendered home page.
 * @constructor
 */
export function HomePage(props){
    const { t } = useTranslation();

    const [applications, setApplications] = useState(null)
    const [errorMessage, setErrorMessage] = useState("")
    const [showSingleApplicant, setShowSingleApplicant] = useState(null)

    const POSSIBLE_FETCH_APPLICATION_ERRORS = [LOGIN_FAIL, APPLICATION_ALREADY_HANDLED,
        PAGE_DOES_NOT_EXIST, SERVER_INTERNAL, INSUFFICIENT_CREDENTIALS, USER_INPUT_ERROR, AUTHENTICATION_FAIL]


    function resolveApiErrors(error) {
        function checkErrorType(possibleError) {
            return error.message === possibleError.errorType
        }
        setErrorMessage(POSSIBLE_FETCH_APPLICATION_ERRORS.find(checkErrorType).message)
    }

    function showApplications() {
        fetchListOfApplications().then(setApplications).catch(resolveApiErrors)
    }

    function handleApplication(applicant) {
        setShowSingleApplicant(applicant)
    }

    function changeStatus(id, newStatus, username, password) {
        function updateApplications(changedApplication) {
            setErrorMessage("")
            setShowSingleApplicant(null)
            return applications.map(application => {
                if (application.id === changedApplication.id)
                    return changedApplication
                else return application
            })
        }
        changeApplicationStatus(id, newStatus, username, password).then(updateApplications).then(setApplications).catch(resolveApiErrors)
    }

    function onCloseHandleApplicantPopup() {
        setShowSingleApplicant(null)
        setErrorMessage("")
    }

    return (<div>
        {errorMessage && !showSingleApplicant && <UserNoticeView message={t(errorMessage)}
                                                                 error={true}/>}
        {props.user.role === 'applicant' && <HomePageApplicantView user={props.user} t={t}/>}
        {props.user.role === 'recruiter' && <HomePageRecruiterView user={props.user}
                                                                   applications={applications}
                                                                   onShowApplications={showApplications}
                                                                   onHandleApplication={handleApplication}
                                                                   t={t}/>}

        <PopupView open={showSingleApplicant} onClose={onCloseHandleApplicantPopup}>
            <HandleApplicationView application={showSingleApplicant}
                                   submitForm={changeStatus}
                                   t={t}
                                   errorMessage={t(errorMessage)}/>
        </PopupView>
    </div>)
}