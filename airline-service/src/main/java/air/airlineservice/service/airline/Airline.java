package air.airlineservice.service.airline;

import air.airlineservice.service.flight.Flight;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "airlines")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Description is mandatory")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Owner is mandatory")
    private String owner;

    @Embedded
    @Column(nullable = false)
    @NotNull(message = "Image is mandatory")
    @Valid
    private Image image;

    @OneToMany(mappedBy = "airline", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Flight> flights;

    /**
     * @return airline builder
     */
    public static Builder builder() {
        return new Airline().new Builder();
    }

    /**
     * Returns an airline builder with predefined fields copied from the specified airline.
     *
     * @param other airline to copy data from
     *
     * @return airline builder
     */
    public static Builder builder(Airline other) {
        return new Airline(other).new Builder();
    }

    /**
     * Constructs a new airline.
     */
    public Airline() {
        flights = new HashSet<>();
    }

    /**
     * Constructs a new airline copying data from the passed one.
     *
     * @param other airline to copy data from
     */
    public Airline(Airline other) {
        id = other.id;
        name = other.name;
        description = other.description;
        owner = other.owner;
        image = (other.image == null) ? null : new Image(other.image);
        flights = new HashSet<>(other.flights);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<Flight> getFlights() {
        return new HashSet<>(flights);
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = new HashSet<>(flights);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Airline airline = (Airline) other;
        return Objects.equals(name, airline.name)
                && Objects.equals(description, airline.description)
                && Objects.equals(owner, airline.owner)
                && Objects.equals(image, airline.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, owner, image);
    }

    @Override
    public String toString() {
        return "Airline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", image=" + image +
                '}';
    }

    /**
     * Airline object builder.
     */
    public class Builder {

        private Builder() {
        }

        public Airline build() {
            return Airline.this;
        }

        public Builder withId(Long id) {
            Airline.this.id = id;
            return this;
        }

        public Builder withName(String name) {
            Airline.this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            Airline.this.description = description;
            return this;
        }

        public Builder withOwner(String owner) {
            Airline.this.owner = owner;
            return this;
        }

        public Builder withImage(Image image) {
            Airline.this.image = image;
            return this;
        }

        public Builder withFlights(Set<Flight> flights) {
            Airline.this.flights = new HashSet<>(flights);
            return this;
        }

        /**
         * Copies not null fields from the specified airline.
         *
         * @param other airline to copy data from
         *
         * @return this builder
         */
        public Builder copyNonNullFields(Airline other) {
            if (other.id != null) {
                Airline.this.id = other.id;
            }
            if (other.name != null) {
                Airline.this.name = other.name;
            }
            if (other.description != null) {
                Airline.this.description = other.description;
            }
            if (other.owner != null) {
                Airline.this.owner = other.owner;
            }
            if (other.image != null) {
                Airline.this.image = new Image(other.image);
            }
            if (other.flights != null) {
                Airline.this.flights = new HashSet<>(other.flights);
            }

            return this;
        }
    }
}
