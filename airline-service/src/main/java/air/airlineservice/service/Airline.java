package air.airlineservice.service;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "airlines")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Description is mandatory")
    private String description;

    @Embedded
    @Column(nullable = false)
    @NotNull(message = "Image is mandatory")
    @Valid
    private Image image;

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
        image = (other.image == null) ? null : new Image(other.image);
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
                && Objects.equals(image, airline.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, image);
    }

    @Override
    public String toString() {
        return "Airline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
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

        public Builder withImage(Image image) {
            Airline.this.image = image;
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
            if (other.image != null) {
                Airline.this.image = new Image(other.image);
            }

            return this;
        }
    }
}
