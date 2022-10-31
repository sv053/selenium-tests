package authservice.service;

import authservice.data.UserRepository;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final CircuitBreaker circuitBreaker;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository,
                                  CircuitBreaker circuitBreaker) {
        this.userRepository = userRepository;
        this.circuitBreaker = circuitBreaker;
    }

    /**
     * Locates the user based on the username.
     *
     * @param username the username identifying the user whose data is required
     *
     * @return a fully populated user record
     *
     * @throws UsernameNotFoundException if the user could not be found
     * @throws IllegalStateException if the user database is not available
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Supplier<Optional<User>> findUser = () -> userRepository.findById(username);
            return circuitBreaker.decorateSupplier(findUser).get().orElseThrow();
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("User not found: " + username, e);
        } catch (Exception e) {
            throw new IllegalStateException("User database unavailable", e);
        }
    }
}
