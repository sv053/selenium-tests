package air.airlineservice.service.flight;

import air.airlineservice.data.FlightRepository;
import air.airlineservice.service.exception.IllegalModificationException;
import air.airlineservice.service.exception.RemoteResourceException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

@Service
@Transactional
public class FlightServiceImpl implements FlightService {
    private static final Logger logger = LogManager.getLogger(FlightServiceImpl.class);

    private final FlightRepository repository;
    private final Validator validator;
    private final CircuitBreaker circuitBreaker;

    public FlightServiceImpl(FlightRepository repository,
                             Validator validator,
                             CircuitBreaker circuitBreaker) {
        this.repository = repository;
        this.validator = validator;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public List<Flight> findAll() {
        try {
            Supplier<List<Flight>> findAll = repository::findAll;
            return circuitBreaker.decorateSupplier(findAll).get();
        } catch (Exception e) {
            throw new RemoteResourceException("Flight database unavailable", e);
        }
    }

    @Override
    public Optional<Flight> findById(Long id) {
        try {
            Supplier<Optional<Flight>> findById = () -> repository.findById(id);
            return circuitBreaker.decorateSupplier(findById).get();
        } catch (Exception e) {
            throw new RemoteResourceException("Flight database unavailable", e);
        }
    }

    @Override
    public Flight save(Flight flight) {
        try {
            validate(flight);
            Flight flightToSave = prepareSaveData(flight);
            Flight saved = persistFlight(flightToSave);
            logger.info("Flight " + saved.getId() + " saved");
            return saved;
        } catch (IllegalModificationException | RemoteResourceException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            String msg = "Such a flight already exists: " + flight.getId();
            throw new IllegalModificationException(msg, e);
        } catch (Exception e) {
            throw new RemoteResourceException("Flight database unavailable", e);
        }
    }

    private void validate(Flight flight) {
        Set<ConstraintViolation<Flight>> violations = validator.validate(flight);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<Flight> violation : violations) {
                builder.append(violation.getMessage()).append(", ");
            }

            builder.delete(builder.length() - 2, builder.length() - 1);
            String msg = builder.toString().toLowerCase(Locale.ROOT);
            throw new IllegalModificationException(msg);
        }
    }

    private Flight prepareSaveData(Flight flight) {
        Flight toSave = new Flight(flight);
        toSave.setId(null);

        return toSave;
    }

    private Flight persistFlight(Flight flight) {
        Supplier<Flight> save = () -> {
            Flight saved = repository.save(flight);
            repository.flush();
            return saved;
        };

        return circuitBreaker.decorateSupplier(save).get();
    }

    @Override
    public Flight update(Flight flight) {
        try {
            long id = flight.getId();
            Flight flightToUpdate = findById(id)
                    .orElseThrow(() -> new IllegalModificationException("No flight with ID " + id));
            flightToUpdate = prepareUpdateData(flightToUpdate, flight);
            validate(flightToUpdate);

            Flight updated = persistFlight(flightToUpdate);
            logger.info("Flight " + updated.getId() + " updated");
            return updated;
        } catch (IllegalModificationException | RemoteResourceException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            String msg = "Such a flight already exists: " + flight.getId();
            throw new IllegalModificationException(msg, e);
        } catch (Exception e) {
            throw new RemoteResourceException("Flight database unavailable", e);
        }
    }

    private Flight prepareUpdateData(Flight savedFlight, Flight updateData) {
        return Flight.builder(savedFlight)
                .copyNonNullFields(updateData)
                .build();
    }

    @Override
    public void deleteById(Long id) {
        try {
            deleteFlight(id);
            logger.info("Flight " + id + " deleted");
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalModificationException("No flight with ID " + id, e);
        } catch (Exception e) {
            throw new RemoteResourceException("Flight database unavailable", e);
        }
    }

    private void deleteFlight(Long id) {
        Runnable delete = () -> {
            repository.deleteById(id);
            repository.flush();
        };

        circuitBreaker.decorateRunnable(delete).run();
    }
}
