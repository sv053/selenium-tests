package air.airlineservice.service.flight;

import air.airlineservice.service.exception.IllegalModificationException;
import air.airlineservice.service.exception.RemoteResourceException;

import java.util.List;
import java.util.Optional;

/**
 * Provides flight business logic.
 */
public interface FlightService {

    /**
     * Looks for all flights in the remote flight repository.
     *
     * @return all the flights from the remote flight repository
     *
     * @throws RemoteResourceException if there is any problem with the remote flight repository
     */
    List<Flight> findAll();

    /**
     * Looks for all flights from the airline with the specified ID in the remote flight repository.
     *
     * @param airlineId ID pf the airline with flights to find
     *
     * @return all the flights from the airline with the specified ID in the remote flight repository
     *
     * @throws RemoteResourceException if there is any problem with the remote flight repository
     */
    List<Flight> findByAirlineId(long airlineId);

    /**
     * Looks for a flight with the specified ID in the remote flight repository.
     *
     * @param id ID of the flight to get
     *
     * @return the flight with the specified ID in the remote flight repository
     * or Optional#empty() if none found
     *
     * @throws RemoteResourceException if there is any problem with the remote flight repository
     */
    Optional<Flight> findById(Long id);

    /**
     * Saves the specified flight in the remote flight repository.
     * Use the returned flight for further operations as the save operation
     * might have changed the flight instance completely.
     *
     * @param flight flight to save
     *
     * @return the saved flight
     *
     * @throws IllegalModificationException if a flight has invalid data
     * @throws RemoteResourceException if there is any problem with the remote flight repository
     */
    Flight save(Flight flight);

    /**
     * Updates the flight with the specified ID in the remote flight repository.
     * Use the returned flight for further operations as the update operation
     * might have changed the flight instance completely.
     *
     * @param flight flight to update
     *
     * @return updated flight
     *
     * @throws IllegalModificationException if a flight has invalid data
     * @throws RemoteResourceException if there is any problem with the remote flight repository
     */
    Flight update(Flight flight);

    /**
     * Deletes the flight with the specified ID from the remote flight repository.
     *
     * @param id the ID of the flight to be deleted
     *
     * @throws IllegalModificationException if such a flight does not exist
     * @throws RemoteResourceException if there is any problem with the remote flight repository
     */
    void deleteById(Long id);
}

