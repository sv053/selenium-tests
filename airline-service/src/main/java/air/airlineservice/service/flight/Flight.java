package air.airlineservice.service.flight;

import air.airlineservice.service.airline.Airline;
import air.airlineservice.service.ticket.Ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Flight domain class.
 */
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    @NotNull(message = "Airline is mandatory")
    private Airline airline;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="country", column= @Column(name="from_country")),
            @AttributeOverride(name="airport", column= @Column(name="from_airport")),
            @AttributeOverride(name="gate", column= @Column(name="from_gate"))
    })
    @NotNull(message = "From is mandatory")
    @Valid
    private Destination from;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="country", column= @Column(name="to_country")),
            @AttributeOverride(name="airport", column= @Column(name="to_airport")),
            @AttributeOverride(name="gate", column= @Column(name="to_gate"))
    })
    @NotNull(message = "To is mandatory")
    @Valid
    private Destination to;

    @Column(name = "date_time", nullable = false)
    @NotNull(message = "Date and time are mandatory")
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Ticket> tickets;

    /**
     * @return flight builder
     */
    public static Builder builder() {
        return new Flight().new Builder();
    }

    /**
     * Returns a flight builder with predefined fields copied from the specified flight.
     *
     * @param other flight to copy data from
     *
     * @return flight builder
     */
    public static Builder builder(Flight other) {
        return new Flight(other).new Builder();
    }

    /**
     * Constructs a new flight.
     */
    public Flight() {
        tickets = new HashSet<>();
    }

    /**
     * Constructs a new flight copying data from the passed one.
     *
     * @param other flight to copy data from
     */
    public Flight(Flight other) {
        id = other.id;
        airline = other.airline;
        from = (other.from == null) ? null : new Destination(other.from);
        to = (other.to == null) ? null : new Destination(other.to);
        dateTime = other.dateTime;
        tickets = new HashSet<>(other.tickets);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Destination getFrom() {
        return from;
    }

    public void setFrom(Destination from) {
        this.from = from;
    }

    public Destination getTo() {
        return to;
    }

    public void setTo(Destination to) {
        this.to = to;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Set<Ticket> getTickets() {
        return new HashSet<>(tickets);
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = new HashSet<>(tickets);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Flight flight = (Flight) other;
        return Objects.equals(airline, flight.airline)
                && Objects.equals(from, flight.from)
                && Objects.equals(to, flight.to)
                && Objects.equals(dateTime, flight.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airline, from, to, dateTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", airline=" + airline +
                ", from=" + from +
                ", to=" + to +
                ", dateTime=" + dateTime +
                '}';
    }

    /**
     * Flight object builder.
     */
    public class Builder {

        private Builder() {
        }

        public Flight build() {
            return Flight.this;
        }

        public Builder withId(Long id) {
            Flight.this.id = id;
            return this;
        }

        public Builder withAirline(Airline airline) {
            Flight.this.airline = airline;
            return this;
        }

        public Builder withFrom(Destination from) {
            Flight.this.from = from;
            return this;
        }

        public Builder withTo(Destination to) {
            Flight.this.to = to;
            return this;
        }

        public Builder withDateTime(LocalDateTime dateTime) {
            Flight.this.dateTime = dateTime;
            return this;
        }

        public Builder withTickets(Set<Ticket> tickets) {
            Flight.this.tickets = new HashSet<>(tickets);
            return this;
        }

        /**
         * Copies not null fields from the specified flight.
         *
         * @param other flight to copy data from
         *
         * @return this builder
         */
        public Builder copyNonNullFields(Flight other) {
            if (other.id != null) {
                Flight.this.id = other.id;
            }
            if (other.airline != null) {
                Flight.this.airline = other.airline;
            }
            if (other.from != null) {
                Flight.this.from = new Destination(other.from);
            }
            if (other.to != null) {
                Flight.this.to = new Destination(other.to);
            }
            if (other.dateTime != null) {
                Flight.this.dateTime = other.dateTime;
            }
            if (other.tickets != null) {
                Flight.this.tickets = new HashSet<>(other.tickets);
            }

            return this;
        }
    }
}
