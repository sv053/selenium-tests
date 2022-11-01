package air.airlineservice.service.ticket;

import air.airlineservice.data.TicketRepository;
import air.airlineservice.service.exception.IllegalModificationException;
import air.airlineservice.service.flight.Flight;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("category.UnitTest")
public class TicketServiceImplTest {
    private static TicketRepository ticketRepository;
    private static Validator validator;
    private static CircuitBreaker circuitBreaker;

    private static Ticket ticket;

    private TicketService ticketService;

    @BeforeAll
    public static void setUpMocks() {
        ticketRepository = mock(TicketRepository.class);
        validator = mock(Validator.class);

        circuitBreaker = mock(CircuitBreaker.class);
        when(circuitBreaker.decorateSupplier(any())).then(returnsFirstArg());
        when(circuitBreaker.decorateRunnable(any())).then(returnsFirstArg());
    }

    @BeforeAll
    public static void createTicket() {
        ticket = Ticket.builder()
                .withFlight(new Flight())
                .withPrice(100L)
                .isLuggageAllowed(true)
                .build();

    }

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(ticketRepository, validator);
        ticketService = new TicketServiceImpl(ticketRepository, validator, circuitBreaker);
    }

    @Test
    public void shouldReturnTicketByIdWhenContainsIt() {
        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        Ticket saved = ticketService.findById(ticket.getId()).orElseThrow();
        assertThat(saved, is(equalTo(ticket)));
    }

    @Test
    public void shouldReturnListOfTicketsWhenContainsMultipleTickets() {
        List<Ticket> tickets = List.of(ticket, ticket, ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> saved = ticketService.findAll();
        assertThat(saved, is(equalTo(tickets)));
    }

    @Test
    public void shouldSaveTicketWhenTicketIsValid() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(validator.validate(any(Ticket.class))).thenReturn(Collections.emptySet());

        Ticket saved = ticketService.save(ticket);
        assertThat(saved, equalTo(ticket));
    }

    @Test
    public void shouldThrowExceptionWhenTicketIsInvalid() {
        when(validator.validate(any(Ticket.class))).thenThrow(IllegalModificationException.class);
        assertThrows(IllegalModificationException.class, () -> ticketService.save(new Ticket()));
    }

    @Test
    public void shouldNotContainTicketWhenDeletesThisTicket() {
        when(ticketRepository.findById(any(Long.class))).thenReturn(Optional.of(ticket));
        doAnswer(invocation -> when(ticketRepository.findById(1L)).thenReturn(Optional.empty()))
                .when(ticketRepository).deleteById(1L);

        ticketService.deleteById(1L);

        Optional<Ticket> deleted = ticketService.findById(1L);
        assertThat(deleted, is(Optional.empty()));
    }
}
