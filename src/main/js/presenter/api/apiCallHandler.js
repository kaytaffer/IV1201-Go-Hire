const baseUrl = '/api'

function sendPostRequest(endpoint, body){
    return fetch(baseUrl + endpoint, {
        method: 'POST',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(body)
    }).then(response => response.json()).then(checkIfServerReturnedError)
}

function sendGetRequest(endpoint) {
    return fetch(baseUrl + endpoint, {
        method: 'GET',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    }).then(response => response.json()).then(checkIfServerReturnedError)
}

function checkIfServerReturnedError(response) {
    if('errorType' in response) {
        throw new Error(response.errorType)
    } else
        return response
}

/**
 * Authenticates login credentials with the API
 * @param username the username
 * @param password the password
 * @returns {Promise<any>} a promise either resolving to a user object or an error object
 */
export function authenticateLogin(username, password){
    return sendPostRequest('/login', {username, password})
}

/**
 * Calls the API to create a new applicant with the supplied arguments.
 * @param firstName the new applicant's first name.
 * @param lastName the new applicant's surname.
 * @param email the new applicant's email.
 * @param personNumber the new applicant's person number.
 * @param username the username.
 * @param password the password.
 * @returns {Promise<any>} a promise either resolving to a user object or an error object
 */
export function createNewApplicant(firstName, lastName, email, personNumber, username, password) {
    return sendPostRequest('/createApplicant', {firstName, lastName, email, personNumber, username, password})
}

/**
 * Tasks server to fetch all applications
 * @returns {Promise<any>} a promise either resolving to an object containing a list of applicants  or an error object
 */
export function fetchListOfApplications() {
    return sendGetRequest('/applications')
}

/**
 * @param id the identifier of the applicant for which to change the status.
 * @param newStatus The new status of the application: 'accepted' or 'rejected'
 * @param username The username of the recruiter changing the status.
 * @param password The username of the recruiter changing the status.
 * @returns {Promise<any>} a promise either resolving to a changed application object or an error object
 */
export function changeApplicationStatus(id, newStatus, username, password) {
    return sendPostRequest('/changeApplicationStatus', {id, newStatus, username, password})
}