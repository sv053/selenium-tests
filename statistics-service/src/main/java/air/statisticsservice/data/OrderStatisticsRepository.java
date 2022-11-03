package air.statisticsservice.data;

import air.statisticsservice.service.OrderStatistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An OrderStatisticsRepository abstracts a collection of OrderStatistics objects.
 */
@Repository
public interface OrderStatisticsRepository extends JpaRepository<OrderStatistics, Long> {

}
