package air.airlineservice.service.ticket;

import air.airlineservice.data.TicketRepository;
import air.airlineservice.service.exception.IllegalModificationException;
import air.airlineservice.service.exception.RemoteResourceException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
    private static final Logger logger = LogManager.getLogger(TicketServiceImpl.class);

    private final TicketRepository repository;
    private final Validator validator;
    private final CircuitBreaker circuitBreaker;

    @Autowired
    public TicketServiceImpl(TicketRepository repository,
                             Validator validator,
                             CircuitBreaker circuitBreaker) {
        this.repository = repository;
        this.validator = validator;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public List<Ticket> findAll() {
        try {
            Supplier<List<Ticket>> findAll = repository::findAll;
            return circuitBreaker.decorateSupplier(findAll).get();
        } catch (Exception e) {
            throw new RemoteResourceException("Ticket database unavailable", e);
        }
    }

    @Override
    public List<Ticket> findByFlightId(long flightId) {
        try {
            Supplier<List<Ticket>> findAll = () -> repository.findAllByFlight(flightId);
            return circuitBreaker.decorateSupplier(findAll).get();
        } catch (Exception e) {
            throw new RemoteResourceException("Ticket database unavailable", e);
        }
    }

    @Override
    public List<Ticket> findByFlightIdAndPrice(long flightId, long price) {
        try {
            Supplier<List<Ticket>> findAll = () -> repository.findAllByFlight(flightId);
            List<Ticket> tickets = circuitBreaker.decorateSupplier(findAll).get();
            return tickets.stream()
                    .filter(ticket -> ticket.getPrice() <= price)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RemoteResourceException("Ticket database unavailable", e);
        }
    }

    @Override
    public List<Ticket> findByFlightIdWithLuggage(long flightId, boolean isAllowed) {
        try {
            Supplier<List<Ticket>> findAll = () -> repository.findAllByFlight(flightId);
            List<Ticket> tickets = circuitBreaker.decorateSupplier(findAll).get();
            return tickets.stream()
                    .filter(ticket -> ticket.getLuggageAllowed() == isAllowed)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RemoteResourceException("Ticket database unavailable", e);
        }
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        try {
            Supplier<Optional<Ticket>> findById = () -> repository.findById(id);
            return circuitBreaker.decorateSupplier(findById).get();
        } catch (Exception e) {
            throw new RemoteResourceException("Ticket database unavailable", e);
        }
    }

    @Override
    public Ticket save(Ticket ticket) {
        try {
            validate(ticket);
            Ticket ticketToSave = prepareSaveData(ticket);
            Ticket saved = persistTicket(ticketToSave);
            logger.info("Ticket " + saved.getId() + " saved");
            return saved;
        } catch (IllegalModificationException | RemoteResourceException e) {
            throw e;
        } catch (Exception e) {
            throw new RemoteResourceException("Ticket database unavailable", e);
        }
    }

    private void validate(Ticket ticket) {
        Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<Ticket> violation : violations) {
                builder.append(violation.getMessage()).append(", ");
            }

            builder.delete(builder.length() - 2, builder.length() - 1);
            String msg = builder.toString().toLowerCase(Locale.ROOT);
            throw new IllegalModificationException(msg);
        }
    }

    private Ticket prepareSaveData(Ticket ticket) {
        Ticket toSave = new Ticket(ticket);
        toSave.setId(null);

        return toSave;
    }

    private Ticket persistTicket(Ticket ticket) {
        Supplier<Ticket> save = () -> {
            Ticket saved = repository.save(ticket);
            repository.flush();
            return saved;
        };

        return circuitBreaker.decorateSupplier(save).get();
    }

    @Override
    public void deleteById(Long id) {
        try {
            deleteTicket(id);
            logger.info("Ticket " + id + " deleted");
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalModificationException("No ticket with ID " + id, e);
        } catch (Exception e) {
            throw new RemoteResourceException("Ticket database unavailable", e);
        }
    }

    private void deleteTicket(Long id) {
        Runnable delete = () -> {
            repository.deleteById(id);
            repository.flush();
        };

        circuitBreaker.decorateRunnable(delete).run();
    }
}
