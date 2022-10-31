package air.airlineservice.service;

import air.airlineservice.data.AirlineRepository;

import air.airlineservice.service.exception.IllegalModificationException;
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
public class AirlineServiceImplTest {
    private static AirlineRepository airlineRepository;
    private static Validator validator;
    private static CircuitBreaker circuitBreaker;

    private static Airline airline;
    private static Airline updatedAirline;

    private AirlineService airlineService;

    @BeforeAll
    public static void setUpMocks() {
        airlineRepository = mock(AirlineRepository.class);
        validator = mock(Validator.class);

        circuitBreaker = mock(CircuitBreaker.class);
        when(circuitBreaker.decorateSupplier(any())).then(returnsFirstArg());
        when(circuitBreaker.decorateRunnable(any())).then(returnsFirstArg());
    }

    @BeforeAll
    public static void createAirline() {
        airline = Airline.builder()
                .withId(1L)
                .withName("name")
                .withDescription("description")
                .withOwner("owner")
                .withImage(new Image("foejfifw"))
                .build();
    }

    @BeforeAll
    public static void createUpdatedAirline() {
        updatedAirline = Airline.builder()
                .withId(1L)
                .withName("name2")
                .withDescription("description2")
                .withOwner("owner2")
                .withImage(new Image("fwfwefefwefef"))
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(airlineRepository, validator);
        airlineService = new AirlineServiceImpl(airlineRepository, validator, circuitBreaker);
    }

    @Test
    public void shouldReturnAirlineByIdWhenContainsIt() {
        when(airlineRepository.findById(airline.getId())).thenReturn(Optional.of(airline));

        Airline saved = airlineService.findById(airline.getId()).orElseThrow();
        assertThat(saved, is(equalTo(airline)));
    }

    @Test
    public void shouldReturnListOfAirlinesWhenContainsMultipleAirlines() {
        List<Airline> airlines = List.of(airline, airline, airline);
        when(airlineRepository.findAll()).thenReturn(airlines);

        List<Airline> saved = airlineService.findAll();
        assertThat(saved, is(equalTo(airlines)));
    }

    @Test
    public void shouldSaveAirlineWhenAirlineIsValid() {
        when(airlineRepository.save(any(Airline.class))).thenReturn(airline);
        when(validator.validate(any(Airline.class))).thenReturn(Collections.emptySet());

        Airline saved = airlineService.save(airline);
        assertThat(saved, equalTo(airline));
    }

    @Test
    public void shouldThrowExceptionWhenAirlineIsInvalid() {
        when(validator.validate(any(Airline.class))).thenThrow(IllegalModificationException.class);
        assertThrows(IllegalModificationException.class, () -> airlineService.save(new Airline()));
    }

    @Test
    public void shouldUpdateAirlineWhenUserIsValid() {
        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));
        when(airlineRepository.save(any(Airline.class))).thenReturn(updatedAirline);
        when(validator.validate(any(Airline.class))).thenReturn(Collections.emptySet());

        Airline updated = airlineService.update(updatedAirline);
        assertThat(updated, equalTo(updatedAirline));
    }

    @Test
    public void shouldNotContainAirlineWhenDeletesThisAirline() {
        when(airlineRepository.findById(any(Long.class))).thenReturn(Optional.of(airline));
        doAnswer(invocation -> when(airlineRepository.findById(1L)).thenReturn(Optional.empty()))
                .when(airlineRepository).deleteById(1L);

        airlineService.deleteById(1L);

        Optional<Airline> deleted = airlineService.findById(1L);
        assertThat(deleted, is(Optional.empty()));
    }
}
