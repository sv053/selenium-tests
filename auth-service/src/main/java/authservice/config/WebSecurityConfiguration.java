package authservice.config;

import authservice.config.properties.UserProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthenticationProvider authenticationProvider;
    private final UserProperties userProperties;

    @Autowired
    public WebSecurityConfiguration(AuthenticationProvider authenticationProvider,
                                    UserProperties userProperties) {
        this.authenticationProvider = authenticationProvider;
        this.userProperties = userProperties;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
        for (UserProperties.User user : userProperties.getUsers()) {
            auth.inMemoryAuthentication()
                    .withUser(user.getName())
                    .password(passwordEncoder().encode(user.getPassword()))
                    .authorities(user.getAuthorities());
       }
    }
}
