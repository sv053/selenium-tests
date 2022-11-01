import getAuthToken from "./AuthApi";
import Config from "./../config.json";

export const postUser = async data => {
    return await fetch(Config.BASE_URL + '/users', {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(handleErrors)
        .then(response => response.json())
}

export const getUserByEmail = async (email, password) => {
    return await getAuthToken(email, password).then(token => {
        return fetch(Config.BASE_URL + 'users?email=' + email, {
            method: 'GET',
            headers: {
                'Authorization': token.token_type + ' ' + token.access_token,
                'Content-Type': 'application/json',
            }
        })
            .then(handleErrors)
            .then(response => response.json())
    })
}

const handleErrors = response => {
    if (!response.ok) {
        throw Error(response.statusText);
    }

    return response;
}
