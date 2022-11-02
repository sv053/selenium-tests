import Config from "./../config.json";

export const getAllTicketsByFlight = async flightId => {
    return await fetch(Config.BASE_URL + "/tickets?flight=" + flightId)
        .then(handleErrors)
        .then(response => response.json())
}

export const getAllTicketsByFlightAndPrice = async (flightId, price) => {
    return await fetch(Config.BASE_URL + "/tickets?flight=" + flightId +
                                         "&price=" + price)
        .then(handleErrors)
        .then(response => response.json())
}

const handleErrors = response => {
    if (!response.ok) {
        throw Error(response.statusText);
    }

    return response;
}

export const getTicketById = async id => {
    return await fetch(Config.BASE_URL  + "/tickets/" + id)
        .then(handleErrors)
        .then(response => response.json())
}
