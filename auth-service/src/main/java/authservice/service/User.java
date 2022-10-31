package authservice.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * User domain class.
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

    /**
     * An enumeration denoting user role.
     */
    public enum Role { USER, ADMIN }

    @Id
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'USER'", nullable = false)
    private Role role;

    /**
     * @return user builder
     */
    public static Builder builder() {
        return new User().new Builder();
    }

    /**
     * Returns a user builder with predefined fields copied from the specified user.
     *
     * @param other user to copy data from
     *
     * @return user builder
     */
    public static Builder builder(User other) {
        return new User(other).new Builder();
    }

    public User() {
    }

    public User(User other) {
        email = other.email;
        password = other.password;
        role = other.role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getLogin() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        User user = (User) other;
        return Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    /**
     * User object builder.
     */
    public class Builder {

        private Builder() {
        }

        public User build() {
            return User.this;
        }

        public Builder withEmail(String email) {
            User.this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            User.this.password = password;
            return this;
        }

        public Builder withRole(Role role) {
            User.this.role = role;
            return this;
        }
    }
}
