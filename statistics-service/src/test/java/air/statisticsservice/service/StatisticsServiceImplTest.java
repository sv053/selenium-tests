package air.statisticsservice.service;

import air.statisticsservice.data.OrderStatisticsRepository;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("category.UnitTest")
public class StatisticsServiceImplTest {
    private static OrderStatisticsRepository repository;
    private static CircuitBreaker circuitBreaker;
    private static KafkaTemplate<String, String> kafkaTemplate;

    private StatisticsServiceImpl statisticsService;

    @BeforeAll
    public static void setUpMocks() {
        repository = mock(OrderStatisticsRepository.class);
        kafkaTemplate = mock(KafkaTemplate.class);

        circuitBreaker = mock(CircuitBreaker.class);
        when(circuitBreaker.decorateSupplier(any())).then(returnsFirstArg());
        when(circuitBreaker.decorateRunnable(any())).then(returnsFirstArg());
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(repository);
        statisticsService = new StatisticsServiceImpl(repository, circuitBreaker, kafkaTemplate);
    }

    @Test
    public void shouldReturnOrderStatisticsWhenContainsIt() {
        OrderStatistics statistics = new OrderStatistics();
        statistics.setId(1L);
        statistics.setAveragePerDay(100L);

        when(repository.findAll()).thenReturn(List.of(statistics));

        OrderStatistics saved = statisticsService.get().orElseThrow();
        assertThat(saved, is(equalTo(statistics)));
    }
}
