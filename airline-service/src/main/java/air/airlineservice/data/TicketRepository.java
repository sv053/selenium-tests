package air.airlineservice.data;

import air.airlineservice.service.ticket.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A TicketRepository abstracts a collection of Ticket objects.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.flight.id = ?1")
    List<Ticket> findAllByFlight(long flightId);
}
