package authservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Client properties loaded from the configuration file.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2")
@Component
public class ClientProperties {
    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public static class Client {
        private String name;
        private String password;
        private int accessTokenValiditySeconds;
        private int refreshTokenValiditySeconds;
        private List<String> grantTypes = new ArrayList<>();
        private List<String> authorities = new ArrayList<>();
        private List<String> scopes = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<String> getGrantTypes() {
            return grantTypes;
        }

        public void setGrantTypes(List<String> grantTypes) {
            this.grantTypes = grantTypes;
        }

        public List<GrantedAuthority> getAuthorities() {
            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        public void setAuthorities(List<String> authorities) {
            this.authorities = authorities;
        }

        public List<String> getScopes() {
            return scopes;
        }

        public void setScopes(List<String> scopes) {
            this.scopes = scopes;
        }

        public int getAccessTokenValiditySeconds() {
            return accessTokenValiditySeconds;
        }

        public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
            this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        }

        public int getRefreshTokenValiditySeconds() {
            return refreshTokenValiditySeconds;
        }

        public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
            this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
        }
    }
}
