package air.statisticsservice.service;

import air.statisticsservice.service.exception.RemoteResourceException;

import java.util.Optional;

/**
 * Provides order statistics business logic.
 */
public interface StatisticsService {

    /**
     * Looks for an order statistics in the statistics database.
     *
     * @return order statistics of Optional#empty() if none found
     *
     * @throws RemoteResourceException if there is any problem with the remote statistics repository
     */
    Optional<OrderStatistics> get();
}
