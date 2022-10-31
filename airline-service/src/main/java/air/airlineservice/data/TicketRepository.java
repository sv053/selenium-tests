package air.airlineservice.data;

import air.airlineservice.service.ticket.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A TicketRepository abstracts a collection of Ticket objects.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
