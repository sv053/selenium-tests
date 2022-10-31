package authservice.config;

import authservice.config.properties.ClientProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class AuthServiceConfiguration extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ClientProperties clientProperties;

    @Value("${spring.security.oauth2.jwt-key}")
    private String jwtKey;

    @Autowired
    public AuthServiceConfiguration(AuthenticationManager authenticationManager,
                                    PasswordEncoder encoder,
                                    ClientProperties clientProperties) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = encoder;
        this.clientProperties = clientProperties;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtKey);
        return converter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsService clientDetailsService = new InMemoryClientDetailsService();
        Map<String, ClientDetails> clientDetailsStore = loadClientDetailsStore();
        clientDetailsService.setClientDetailsStore(clientDetailsStore);
        clients.withClientDetails(clientDetailsService);
    }

    private Map<String, ClientDetails> loadClientDetailsStore() {
        Map<String, ClientDetails> clientDetailsStore = new HashMap<>();
        clientProperties.getClients().forEach(client -> {
            BaseClientDetails clientDetails = extractClientDetails(client);
            clientDetailsStore.put(client.getName(), clientDetails);
        });

        return clientDetailsStore;
    }

    private BaseClientDetails extractClientDetails(ClientProperties.Client client) {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(client.getName());
        clientDetails.setClientSecret(passwordEncoder.encode(client.getPassword()));
        clientDetails.setScope(client.getScopes());
        clientDetails.setAuthorizedGrantTypes(client.getGrantTypes());
        clientDetails.setAuthorities(client.getAuthorities());
        clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
        clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());

        return clientDetails;
    }
}
