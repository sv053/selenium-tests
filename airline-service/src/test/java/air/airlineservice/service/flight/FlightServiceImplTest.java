package air.airlineservice.service.flight;

import air.airlineservice.data.FlightRepository;
import air.airlineservice.service.airline.Airline;
import air.airlineservice.service.airline.Image;
import air.airlineservice.service.exception.IllegalModificationException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import javax.validation.Validator;

import java.time.LocalDateTime;
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
public class FlightServiceImplTest {
    private static FlightRepository flightRepository;
    private static Validator validator;
    private static CircuitBreaker circuitBreaker;

    private static Flight flight;
    private static Flight updatedFlight;

    private FlightService flightService;

    @BeforeAll
    public static void setUpMocks() {
        flightRepository = mock(FlightRepository.class);
        validator = mock(Validator.class);

        circuitBreaker = mock(CircuitBreaker.class);
        when(circuitBreaker.decorateSupplier(any())).then(returnsFirstArg());
        when(circuitBreaker.decorateRunnable(any())).then(returnsFirstArg());
    }

    @BeforeAll
    public static void createFlight() {
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

        flight = Flight.builder()
                .withId(1L)
                .withAirline(airline)
                .withFrom(from)
                .withTo(to)
                .withDateTime(LocalDateTime.now())
                .build();
    }

    @BeforeAll
    public static void createUpdatedFlight() {
        Airline airline = Airline.builder()
                .withName("name2")
                .withDescription("description2")
                .withOwner("owner2")
                .withImage(new Image("dffdffsdfds"))
                .build();
        Destination from = Destination.builder()
                .withCountry("Country2")
                .withAirport("airport2")
                .withGate(1L)
                .build();
        Destination to = Destination.builder()
                .withCountry("Country3")
                .withAirport("airport3")
                .withGate(10L)
                .build();

        updatedFlight = Flight.builder()
                .withId(1L)
                .withAirline(airline)
                .withFrom(from)
                .withTo(to)
                .withDateTime(LocalDateTime.now())
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(flightRepository, validator);
        flightService = new FlightServiceImpl(flightRepository, validator, circuitBreaker);
    }

    @Test
    public void shouldReturnFlightByIdWhenContainsIt() {
        when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));

        Flight saved = flightService.findById(flight.getId()).orElseThrow();
        assertThat(saved, is(equalTo(flight)));
    }

    @Test
    public void shouldReturnListOfFlightsWhenContainsMultipleFlights() {
        List<Flight> flights = List.of(flight, flight, flight);
        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> saved = flightService.findAll();
        assertThat(saved, is(equalTo(flights)));
    }

    @Test
    public void shouldReturnListOfFlightsByAirlineId() {
        List<Flight> flights = List.of(flight, flight, flight);
        when(flightRepository.findAllByAirlineId(1)).thenReturn(flights);

        List<Flight> saved = flightService.findByAirlineId(1);
        assertThat(saved, is(equalTo(flights)));
    }

    @Test
    public void shouldSaveFlightWhenFlightIsValid() {
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(validator.validate(any(Flight.class))).thenReturn(Collections.emptySet());

        Flight saved = flightService.save(flight);
        assertThat(saved, equalTo(flight));
    }

    @Test
    public void shouldThrowExceptionWhenFlightIsInvalid() {
        when(validator.validate(any(Flight.class))).thenThrow(IllegalModificationException.class);
        assertThrows(IllegalModificationException.class, () -> flightService.save(new Flight()));
    }

    @Test
    public void shouldUpdateFlightWhenFlightIsValid() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenReturn(updatedFlight);
        when(validator.validate(any(Flight.class))).thenReturn(Collections.emptySet());

        Flight updated = flightService.update(updatedFlight);
        assertThat(updated, equalTo(updatedFlight));
    }

    @Test
    public void shouldNotContainFlightWhenDeletesThisFlight() {
        when(flightRepository.findById(any(Long.class))).thenReturn(Optional.of(flight));
        doAnswer(invocation -> when(flightRepository.findById(1L)).thenReturn(Optional.empty()))
                .when(flightRepository).deleteById(1L);

        flightService.deleteById(1L);

        Optional<Flight> deleted = flightService.findById(1L);
        assertThat(deleted, is(Optional.empty()));
    }
}
