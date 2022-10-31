package authservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("category.UnitTest")
public class AuthProviderServiceImplTest {
    private static UserDetailsService userDetailsService;
    private static PasswordEncoder encoder;

    private static User user;

    private AuthProviderServiceImpl authProviderService;

    @BeforeAll
    public static void setUpMocks() {
        userDetailsService = mock(UserDetailsServiceImpl.class);

        encoder = mock(PasswordEncoder.class);
        when(encoder.encode(anyString())).then(returnsFirstArg());
        when(encoder.matches(anyString(), anyString())).then(invocation -> {
            String rawPassword = invocation.getArgument(0);
            String encodedPassword = invocation.getArgument(1);
            return rawPassword.equals(encodedPassword);
        });
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
        Mockito.reset(userDetailsService);
        authProviderService = new AuthProviderServiceImpl(userDetailsService, encoder);
    }

    @Test
    public void shouldAuthenticateUser() {
        when(userDetailsService.loadUserByUsername(user.getLogin())).thenReturn(user);
        Authentication userAuth = new UsernamePasswordAuthenticationToken(
                user.getLogin(), user.getPassword(), user.getAuthorities()
        );
        userAuth.setAuthenticated(false);

        Authentication authentication = authProviderService.authenticate(userAuth);
        assertThat(authentication.isAuthenticated(), equalTo(true));
    }
}
