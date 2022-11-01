package air.airlineservice.service.flight;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.util.Objects;

@Embeddable
public class Destination {

    @Column(nullable = false)
    @NotBlank(message = "Country is mandatory")
    private String country;

    @Column(nullable = false)
    @NotBlank(message = "Airport is mandatory")
    private String airport;

    @Column(nullable = false)
    @NotNull(message = "Gate is mandatory")
    @Positive(message = "Gate must be positive")
    private Long gate;

    /**
     * @return destination builder
     */
    public static Builder builder() {
        return new Destination().new Builder();
    }

    /**
     * Returns a destination builder with predefined fields copied from the specified destination.
     *
     * @param data destination to copy data from
     *
     * @return destination builder
     */
    public static Builder builder(Destination data) {
        return new Destination(data).new Builder();
    }

    public Destination() {
    }

    /**
     * Constructs a new destination copying data from the passed one.
     *
     * @param other destination to copy data from
     */
    public Destination(Destination other) {
        country = other.country;
        airport = other.airport;
        gate = other.gate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public Long getGate() {
        return gate;
    }

    public void setGate(Long gate) {
        this.gate = gate;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Destination destination = (Destination) other;
        return Objects.equals(country, destination.country)
                && Objects.equals(airport, destination.airport)
                && Objects.equals(gate, destination.gate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, airport, gate);
    }

    @Override
    public String toString() {
        return "Destination{" +
                "country='" + country + '\'' +
                ", airport='" + airport + '\'' +
                ", gate=" + gate +
                '}';
    }

    /**
     * Destination object builder.
     */
    public class Builder {

        private Builder() {
        }

        public Destination build() {
            return Destination.this;
        }

        public Builder withCountry(String country) {
            Destination.this.country = country;
            return this;
        }

        public Builder withAirport(String airport) {
            Destination.this.airport = airport;
            return this;
        }

        public Builder withGate(Long gate) {
            Destination.this.gate = gate;
            return this;
        }

        /**
         * Copies not null fields from the specified destination.
         *
         * @param destination destination to copy data from
         *
         * @return this builder
         */
        public Builder copyNonNullFields(Destination destination) {
            if (destination.country != null) {
                Destination.this.country = destination.country;
            }
            if (destination.airport != null) {
                Destination.this.airport = destination.airport;
            }
            if (destination.gate != null) {
                Destination.this.gate = destination.gate;
            }

            return this;
        }
    }
}
