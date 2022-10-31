package air.airlineservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag("category.UnitTest")
public class AirlineTest {
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

        int errors = validator.validate(airline).size();
        assertThat(errors, is(0));
    }

    @Test
    public void shouldNotPassValidationWhenHasInvalidData() {
        Airline airline = new Airline();

        int errors = validator.validate(airline).size();
        assertThat(errors, is(4));
    }
}
