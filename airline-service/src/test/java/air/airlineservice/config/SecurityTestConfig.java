package air.airlineservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class SecurityTestConfig implements ResourceServerConfigurer {
    private final ResourceServerConfiguration configuration;

    @Autowired
    public SecurityTestConfig(ResourceServerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer security) {
        configuration.configure(security);
        security.stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        configuration.configure(http);
    }
}
