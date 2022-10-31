package air.userservice.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import java.util.Objects;

/**
 * User domain class
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false)
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is mandatory")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

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

    /**
     * Constructs a new user.
     */
    public User() {
    }

    /**
     * Constructs a new User copying data from the passed one.
     *
     * @param other user to copy data from
     */
    public User(User other) {
        email = other.email;
        password = other.password;
        name = other.name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
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

        public Builder withName(String name) {
            User.this.name = name;
            return this;
        }

        /**
         * Copies not null fields from the specified user.
         *
         * @param other user to copy data from
         *
         * @return this builder
         */
        public Builder copyNonNullFields(User other) {
            if (other.email != null) {
                User.this.email = other.email;
            }
            if (other.password != null) {
                User.this.password = other.password;
            }
            if (other.name != null) {
                User.this.name = other.name;
            }

            return this;
        }
    }
}
