package air.airlineservice.service.ticket;

import air.airlineservice.service.flight.Flight;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag("category.UnitTest")
public class TicketTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldPassValidationWhenHasValidData() {
        Ticket ticket = Ticket.builder()
                .withFlight(new Flight())
                .withPrice(100L)
                .build();

        int errors = validator.validate(ticket).size();
        assertThat(errors, is(0));
    }

    @Test
    public void shouldNotPassValidationWhenHasInvalidData() {
        Ticket ticket = new Ticket();

        int errors = validator.validate(ticket).size();
        assertThat(errors, is(2));
    }
}
