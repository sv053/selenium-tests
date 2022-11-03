import getAuthToken from "./AuthApi";
import Config from "./../config.json";

export const postBooking = async data => {
    return await getAuthToken(data.email, data.password).then(token => {
        return fetch(Config.BASE_URL + '/booking', {
            method: 'POST',
            body: JSON.stringify(data.tickets),
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
