import Config from "../config.json";

const getAuthToken = async (username, password) => {
    const clientId = Config.OAUTH_CLIENT_ID
    const clientSecret = Config.OAUTH_CLIENT_SECRET
    return await fetch(Config.BASE_URL + "/oauth/token", {
        method: 'POST',
        body: 'grant_type=password&' +
            'client_id=' + clientId + '&' +
            'client_secret=' + clientSecret + '&' +
            'username=' + username + '&' +
            'password=' + password + '&' +
            'scope=write',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
        .then(handleErrors)
        .then(response => response.json())
}

const handleErrors = response => {
    if (!response.ok) {
        throw Error(response.statusText);
    }

    return response;
}

export default getAuthToken
