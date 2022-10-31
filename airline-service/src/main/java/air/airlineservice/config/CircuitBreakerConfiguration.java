package air.airlineservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public CircuitBreaker circuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig
                .custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(10)
                .slowCallRateThreshold(70.0f)
                .slowCallDurationThreshold(Duration.ofSeconds(2))
                .recordExceptions(InvalidDataAccessResourceUsageException.class)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        return registry.circuitBreaker("circuitBreaker");
    }
}
