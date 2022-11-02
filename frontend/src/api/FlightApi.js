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

export const getFlightByAirlineName = async name => {
    return await fetch(Config.BASE_URL  + "/flights?airlineName=" + name)
        .then(handleErrors)
        .then(response => response.json())
}

export const getFlightByOriginAndDest = async (origin, destination) => {
    return await fetch(Config.BASE_URL  + "/flights?origin=" + origin +
                                          "&destination=" + destination)
        .then(handleErrors)
        .then(response => response.json())
}

export const getFlightByAirlineAndWay = async (airline, origin, destination) => {
    return await fetch(Config.BASE_URL  + "/flights?origin=" + origin +
                                          "&destination=" + destination +
                                          "&airline=" + airline)
        .then(handleErrors)
        .then(response => response.json())
}

