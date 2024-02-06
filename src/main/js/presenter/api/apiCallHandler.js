
function sendPostRequest(endpoint, body){
    return fetch(endpoint, {
        method: 'POST',
        body: JSON.stringify(body)
    }).then(response => response.json());
}
export function authenticateLogin(username, password){
    return sendPostRequest('/api/login', {username, password})
}
