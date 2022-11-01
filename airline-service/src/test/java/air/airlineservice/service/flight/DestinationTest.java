package air.airlineservice.service.flight;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag("category.UnitTest")
public class DestinationTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldPassValidationWhenHasValidData() {
        Destination destination = Destination.builder()
                .withCountry("Country")
                .withAirport("airport")
                .withGate(3L)
                .build();

        int errors = validator.validate(destination).size();
        assertThat(errors, is(0));
    }

    @Test
    public void shouldNotPassValidationWhenHasInvalidData() {
        Destination destination = Destination.builder()
                .withGate(-1L)
                .build();

        int errors = validator.validate(destination).size();
        assertThat(errors, is(3));
    }
}
