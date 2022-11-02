package air.airlineservice.service.flight;

import air.airlineservice.service.airline.Airline;
import air.airlineservice.service.airline.Image;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag("category.UnitTest")
public class FlightTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldPassValidationWhenHasValidData() {
        Airline airline = Airline.builder()
                .withName("name")
                .withDescription("description")
                .withOwner("owner")
                .withImage(new Image("foejfifw"))
                .build();
        Destination from = Destination.builder()
                .withCountry("Country")
                .withAirport("airport")
                .withGate(3L)
                .build();
        Destination to = Destination.builder()
                .withCountry("Country2")
                .withAirport("airport2")
                .withGate(2L)
                .build();

        Flight flight = Flight.builder()
                .withAirline(airline)
                .withFrom(from)
                .withTo(to)
                .withDateTime(LocalDateTime.now())
                .build();

        int errors = validator.validate(flight).size();
        assertThat(errors, is(0));
    }

    @Test
    public void shouldNotPassValidationWhenHasInvalidData() {
        Flight flight = Flight.builder()
                .withTo(new Destination())
                .withFrom(new Destination())
                .build();

        int errors = validator.validate(flight).size();
        assertThat(errors, is(8));
    }
}
