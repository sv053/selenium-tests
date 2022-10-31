package air.airlineservice.service;

import air.airlineservice.data.AirlineRepository;

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
public class AirlineServiceImpl implements AirlineService {
    private static final Logger logger = LogManager.getLogger(AirlineServiceImpl.class);

    private final AirlineRepository repository;
    private final Validator validator;
    private final CircuitBreaker circuitBreaker;

    public AirlineServiceImpl(AirlineRepository repository,
                              Validator validator,
                              CircuitBreaker circuitBreaker) {
        this.repository = repository;
        this.validator = validator;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public List<Airline> findAll() {
        try {
            Supplier<List<Airline>> findAll = repository::findAll;
            return circuitBreaker.decorateSupplier(findAll).get();
        } catch (Exception e) {
            throw new RemoteResourceException("Airline database unavailable", e);
        }
    }

    @Override
    public Optional<Airline> findById(Long id) {
        try {
            Supplier<Optional<Airline>> findById = () -> repository.findById(id);
            return circuitBreaker.decorateSupplier(findById).get();
        } catch (Exception e) {
            throw new RemoteResourceException("Airline database unavailable", e);
        }
    }

    @Override
    public Airline save(Airline airline) {
        try {
            validate(airline);
            Airline airlineToSave = prepareSaveData(airline);
            Airline saved = persistAirline(airlineToSave);
            logger.info("Airline " + saved.getId() + " saved");
            return saved;
        } catch (IllegalModificationException | RemoteResourceException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            String msg = "Such an airline already exists: " + airline.getId();
            throw new IllegalModificationException(msg, e);
        } catch (Exception e) {
            throw new RemoteResourceException("Airline database unavailable", e);
        }
    }

    private void validate(Airline airline) {
        Set<ConstraintViolation<Airline>> violations = validator.validate(airline);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<Airline> violation : violations) {
                builder.append(violation.getMessage()).append(", ");
            }

            builder.delete(builder.length() - 2, builder.length() - 1);
            String msg = builder.toString().toLowerCase(Locale.ROOT);
            throw new IllegalModificationException(msg);
        }
    }

    private Airline prepareSaveData(Airline airline) {
        Airline toSave = new Airline(airline);
        toSave.setId(null);

        return toSave;
    }

    private Airline persistAirline(Airline airline) {
        Supplier<Airline> save = () -> {
            Airline saved = repository.save(airline);
            repository.flush();
            return saved;
        };

        return circuitBreaker.decorateSupplier(save).get();
    }

    @Override
    public Airline update(Airline airline) {
        try {
            long id = airline.getId();
            Airline airlineToUpdate = findById(id)
                    .orElseThrow(() -> new IllegalModificationException("No airline with ID " + id));
            airlineToUpdate = prepareUpdateData(airlineToUpdate, airline);
            validate(airlineToUpdate);

            Airline updated = persistAirline(airlineToUpdate);
            logger.info("Airline " + updated.getId() + " updated");
            return updated;
        } catch (IllegalModificationException | RemoteResourceException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            String msg = "Such an airline already exists: " + airline.getId();
            throw new IllegalModificationException(msg, e);
        } catch (Exception e) {
            throw new RemoteResourceException("Airline database unavailable", e);
        }
    }

    private Airline prepareUpdateData(Airline savedAirline, Airline updateData) {
        return Airline.builder(savedAirline)
                .copyNonNullFields(updateData)
                .build();
    }

    @Override
    public void deleteById(Long id) {
        try {
            deleteAirline(id);
            logger.info("Airline " + id + " deleted");
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalModificationException("No airline with ID " + id, e);
        } catch (Exception e) {
            throw new RemoteResourceException("Airline database unavailable", e);
        }
    }

    private void deleteAirline(Long id) {
        Runnable delete = () -> {
            repository.deleteById(id);
            repository.flush();
        };

        circuitBreaker.decorateRunnable(delete).run();
    }
}
