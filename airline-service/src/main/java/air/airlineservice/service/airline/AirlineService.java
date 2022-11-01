package air.airlineservice.service.airline;

import air.airlineservice.service.exception.IllegalModificationException;
import air.airlineservice.service.exception.RemoteResourceException;

import java.util.List;
import java.util.Optional;

/**
 * Provides airline business logic.
 */
public interface AirlineService {

    /**
     * Looks for al airlines with in the remote airline repository.
     *
     * @return all the airlines from the remote airline repository
     *
     * @throws RemoteResourceException if there is any problem with the remote airline repository
     */
    List<Airline> findAll();

    /**
     * Looks for an airline with the specified ID in the remote airline repository.
     *
     * @param id ID of the airline to get
     *
     * @return the airline with the specified ID in the remote airline repository
     * or Optional#empty() if none found
     *
     * @throws RemoteResourceException if there is any problem with the remote airline repository
     */
    Optional<Airline> findById(Long id);

    /**
     * Looks for an airline with the specified name in the remote airline repository.
     *
     * @param name name of the airline to get
     *
     * @return the airline with the specified name in the remote airline repository
     * or Optional#empty() if none found
     *
     * @throws RemoteResourceException if there is any problem with the remote airline repository
     */
    Optional<Airline> findByName(String name);

    /**
     * Saves the specified airline in the remote airline repository.
     * Use the returned airline for further operations as the save operation
     * might have changed the airline instance completely.
     *
     * @param airline airline to save
     *
     * @return the saved airline
     *
     * @throws IllegalModificationException either if a airline has invalid data or already exists
     * @throws RemoteResourceException if there is any problem with the remote airline repository
     */
    Airline save(Airline airline);

    /**
     * Updates the airline with the specified ID in the remote airline repository.
     * Use the returned airline for further operations as the update operation
     * might have changed the airline instance completely.
     *
     * @param airline airline to update
     *
     * @return the updated airline
     *
     * @throws IllegalModificationException either if an airline has invalid data or does not exist
     * @throws RemoteResourceException if there is any problem with the remote airline repository
     */
    Airline update(Airline airline);

    /**
     * Deletes the airline with the specified ID in the remote airline repository.
     *
     * @param id the ID of the airline to be deleted
     *
     * @throws IllegalModificationException if such an airline does not exist
     * @throws RemoteResourceException if there is any problem with the remote airline repository
     */
    void deleteById(Long id);
}

