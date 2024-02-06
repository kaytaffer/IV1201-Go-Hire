function sendPostRequest(endpoint, body){
    return fetch(endpoint, {
        method: 'POST',
        body: JSON.stringify(body)
    }).then(response => response.json());
}

/**
 * Authenticates login credentials with the API
 * @param username the username
 * @param password the password
 * @returns {Promise<any>} a promise either resolving to a user object or an error object
 */
export function authenticateLogin(username, password){
    return sendPostRequest('/api/login', {username, password})
}
