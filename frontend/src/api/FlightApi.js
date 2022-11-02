import Config from "./../config.json";

export const getAllFlights = async () => {
    return await fetch(Config.BASE_URL + "/flights")
        .then(handleErrors)
        .then(response => response.json())
}

const handleErrors = response => {
    if (!response.ok) {
        throw Error(response.statusText);
    }

    return response;
}

export const getFlightById = async id => {
    return await fetch(Config.BASE_URL  + "/flights/" + id)
        .then(handleErrors)
        .then(response => response.json())
}
