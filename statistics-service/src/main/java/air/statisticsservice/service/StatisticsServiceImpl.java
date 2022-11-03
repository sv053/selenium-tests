package air.statisticsservice.service;

import air.statisticsservice.data.OrderStatisticsRepository;
import air.statisticsservice.service.exception.RemoteResourceException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {
    private final static Logger logger = LogManager.getLogger(StatisticsService.class);

    private final OrderStatisticsRepository repository;
    private final CircuitBreaker circuitBreaker;

    @Autowired
    public StatisticsServiceImpl(OrderStatisticsRepository repository,
                                 CircuitBreaker circuitBreaker) {
        this.repository = repository;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public Optional<OrderStatistics> get() {
        try {
            Supplier<List<OrderStatistics>> find = repository::findAll;
            List<OrderStatistics> statistics = circuitBreaker.decorateSupplier(find).get();
            return (statistics.isEmpty()) ? Optional.empty() : Optional.of(statistics.get(0));
        } catch (Exception e) {
            throw new RemoteResourceException("Statistics database unavailable", e);
        }
    }
}
