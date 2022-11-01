package air.userservice.service;

import air.userservice.service.exception.IllegalModificationException;
import air.userservice.service.exception.RemoteResourceException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import air.userservice.data.UserRepository;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;
    private final CircuitBreaker circuitBreaker;

    @Autowired
    public UserServiceImpl(UserRepository repository,
                           PasswordEncoder passwordEncoder,
                           Validator validator,
                           CircuitBreaker circuitBreaker) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Supplier<Optional<User>> findByEmail = () -> repository.findById(email);
            return circuitBreaker.decorateSupplier(findByEmail).get();
        } catch (Exception e) {
            throw new RemoteResourceException("User database unavailable", e);
        }
    }

    @Override
    public User save(User user) {
        try {
            validate(user);
            User userToSave = prepareSaveData(user);
            User saved = persistUser(userToSave);
            logger.info("User " + saved.getEmail() + " saved");
            return saved;
        } catch (IllegalModificationException | RemoteResourceException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            String msg = "Such a user already exists: " + user.getEmail();
            throw new IllegalModificationException(msg, e);
        } catch (Exception e) {
            throw new RemoteResourceException("User database unavailable", e);
        }
    }

    private void validate(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<User> violation : violations) {
                builder.append(violation.getMessage()).append(", ");
            }

            builder.delete(builder.length() - 2, builder.length() - 1);
            String msg = builder.toString().toLowerCase(Locale.ROOT);
            throw new IllegalModificationException(msg);
        }
    }

    private User prepareSaveData(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        User userToSave = new User(user);
        userToSave.setPassword(password);

        return userToSave;
    }

    private User persistUser(User user) {
        Supplier<User> save = () -> {
            User saved = repository.save(user);
            repository.flush();
            return saved;
        };

        return circuitBreaker.decorateSupplier(save).get();
    }

    @Override
    public User update(User user) {
        try {
            String login = user.getEmail();
            User userToUpdate = findByEmail(login)
                    .orElseThrow(() -> new IllegalModificationException("No user with email " + login));
            userToUpdate = prepareUpdateData(userToUpdate, user);
            validate(userToUpdate);

            User updated = persistUser(userToUpdate);
            logger.info("User " + updated.getEmail() + " updated");
            return updated;
        } catch (IllegalModificationException | RemoteResourceException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            String msg = "Such a user already exists: " + user.getEmail();
            throw new IllegalModificationException(msg, e);
        } catch (Exception e) {
            throw new RemoteResourceException("User database unavailable", e);
        }
    }

    private User prepareUpdateData(User savedUser, User updateData) {
        return User.builder(savedUser)
                .copyNonNullFields(updateData)
                .build();
    }

    @Override
    public void deleteByEmail(String email) {
        try {
            deleteUser(email);
            logger.info("User " + email + " deleted");
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalModificationException("No user with email " + email, e);
        } catch (Exception e) {
            throw new RemoteResourceException("User database unavailable", e);
        }
    }

    private void deleteUser(String login) {
        Runnable delete = () -> {
            repository.deleteById(login);
            repository.flush();
        };

        circuitBreaker.decorateRunnable(delete).run();
    }
}
