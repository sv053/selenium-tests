package air.airlineservice.service.ticket;

import air.airlineservice.service.exception.IllegalModificationException;
import air.airlineservice.service.exception.RemoteResourceException;

import java.util.List;
import java.util.Optional;

/**
 * Provides ticket business logic.
 */
public interface TicketService {

    /**
     * Looks for all tickets in the remote ticket repository.
     *
     * @return all the tickets from the remote ticket repository
     *
     * @throws RemoteResourceException if there is any problem with the remote ticket repository
     */
    List<Ticket> findAll();

    /**
     * Looks for all tickets with the specified flight ID in the remote ticket repository.
     *
     * @return all the tickets with the specified flight ID from the remote ticket repository
     *
     * @throws RemoteResourceException if there is any problem with the remote ticket repository
     */
    List<Ticket> findByFlightId(long flightId);

    /**
     * Looks for all tickets with the specified flight ID in the remote ticket repository
     * which price is lower that or equal to the specified price.
     *
     * @param price price to match
     *
     * @return all tickets with the specified flight ID in the remote ticket repository
     * which price is lower that or equal to the specified price.
     *
     * @throws RemoteResourceException if there is any problem with the remote ticket repository
     */
    List<Ticket> findByFlightIdAndPrice(long flightId, long price);

    /**
     * Looks for all tickets with the specified flight ID in the remote ticket repository
     * which allow luggage.
     *
     * @param isAllowed is luggage allowed
     *
     * @return all tickets with the specified flight ID in the remote ticket repository
     * which allow luggage.
     *
     * @throws RemoteResourceException if there is any problem with the remote ticket repository
     */
    List<Ticket> findByFlightIdWithLuggage(long flightId, boolean isAllowed);

    /**
     * Looks for a ticket with the specified ID in the remote ticket repository.
     *
     * @param id ID of the ticket to get
     *
     * @return the ticket with the specified ID in the remote ticket repository
     * or Optional#empty() if none found
     *
     * @throws RemoteResourceException if there is any problem with the remote ticket repository
     */
    Optional<Ticket> findById(Long id);

    /**
     * Saves the specified ticket in the remote ticket repository.
     * Use the returned ticket for further operations as the save operation
     * might have changed the ticket instance completely.
     *
     * @param ticket ticket to save
     *
     * @return the saved ticket
     *
     * @throws IllegalModificationException either if a ticket has invalid data or already exists
     * @throws RemoteResourceException if there is any problem with the remote ticket repository
     */
    Ticket save(Ticket ticket);

    /**
     * Deletes the ticket with the specified ID from the remote ticket repository.
     *
     * @param id the ID of the ticket to be deleted
     *
     * @throws IllegalModificationException if such a ticket does not exist
     * @throws RemoteResourceException if there is any problem with the remote ticket repository
     */
    void deleteById(Long id);
}

