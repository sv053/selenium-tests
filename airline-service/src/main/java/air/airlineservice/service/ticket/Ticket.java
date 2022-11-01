package air.airlineservice.service.ticket;

import air.airlineservice.service.flight.Flight;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Ticket domain class.
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @NotNull(message = "Flight is mandatory")
    private Flight flight;

    @Column(nullable = false)
    @NotNull(message = "Price is mandatory")
    private Long price;

    @Column(name = "luggage_allowed", nullable = false)
    @NotNull(message = "Luggage allowance is mandatory")
    private Boolean luggageAllowed;

    /**
     * @return ticket builder
     */
    public static Builder builder() {
        return new Ticket().new Builder();
    }

    /**
     * Returns a ticket builder with predefined fields copied from the specified ticket.
     *
     * @param other ticket to copy data from
     *
     * @return ticket builder
     */
    public static Builder builder(Ticket other) {
        return new Ticket(other).new Builder();
    }

    /**
     * Constructs a new ticket.
     */
    public Ticket() {
    }

    /**
     * Constructs a new ticket copying data from the passed one.
     *
     * @param other ticket to copy data from
     */
    public Ticket(Ticket other) {
        id = other.id;
        flight = other.flight;
        price = other.price;
        luggageAllowed = other.luggageAllowed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getLuggageAllowed() {
        return luggageAllowed;
    }

    public void setLuggageAllowed(Boolean luggageAllowed) {
        this.luggageAllowed = luggageAllowed;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Ticket ticket = (Ticket) other;
        return Objects.equals(flight, ticket.flight)
                && Objects.equals(price, ticket.price)
                && Objects.equals(luggageAllowed, ticket.luggageAllowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight, price, luggageAllowed);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", flight=" + flight +
                ", price=" + price +
                ", luggageAllowed=" + luggageAllowed +
                '}';
    }

    /**
     * Ticket object builder.
     */
    public class Builder {

        private Builder() {
        }

        public Ticket build() {
            return Ticket.this;
        }

        public Builder withId(Long id) {
            Ticket.this.id = id;
            return this;
        }

        public Builder withFlight(Flight flight) {
            Ticket.this.flight = flight;
            return this;
        }

        public Builder withPrice(Long price) {
            Ticket.this.price = price;
            return this;
        }

        public Builder isLuggageAllowed(Boolean isAllowed) {
            Ticket.this.luggageAllowed = isAllowed;
            return this;
        }

        /**
         * Copies not null fields from the specified ticket.
         *
         * @param other ticket to copy data from
         *
         * @return this builder
         */
        public Builder copyNonNullFields(Ticket other) {
            if (other.id != null) {
                Ticket.this.id = other.id;
            }
            if (other.flight != null) {
                Ticket.this.flight = other.flight;
            }
            if (other.price != null) {
                Ticket.this.price = other.price;
            }
            if (other.luggageAllowed != null) {
                Ticket.this.luggageAllowed = other.luggageAllowed;
            }

            return this;
        }
    }
}
