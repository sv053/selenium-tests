package authservice.service;

import authservice.data.UserRepository;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("category.UnitTest")
public class UserDetailsServiceImplTest {
    private static UserRepository userRepository;
    private static CircuitBreaker circuitBreaker;

    private static User user;

    private UserDetailsServiceImpl userDetailsService;

    @BeforeAll
    public static void setUpMocks() {
        userRepository = mock(UserRepository.class);

        circuitBreaker = mock(CircuitBreaker.class);
        when(circuitBreaker.decorateSupplier(any())).then(returnsFirstArg());
        when(circuitBreaker.decorateRunnable(any())).then(returnsFirstArg());
    }

    @BeforeAll
    public static void createUser() {
        user = User.builder()
                .withEmail("user@gmail.com")
                .withPassword("12345678")
                .withRole(User.Role.USER)
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(userRepository);
        userDetailsService = new UserDetailsServiceImpl(userRepository, circuitBreaker);
    }

    @Test
    public void shouldReturnUserByUsernameWhenContainsIt() {
        String username = user.getLogin();
        when(userRepository.findById(username)).thenReturn(Optional.of(user));

        UserDetails saved = userDetailsService.loadUserByUsername(username);
        assertThat(saved, is(equalTo(user)));
    }
}
