package air.airlineservice.data;

import air.airlineservice.service.Airline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An AirlineRepository abstracts a collection of Airline objects.
 */
@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
