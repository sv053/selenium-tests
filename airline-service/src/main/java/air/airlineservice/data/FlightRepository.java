package air.airlineservice.data;

import air.airlineservice.service.flight.Flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A FlightRepository abstracts a collection of Flight objects.
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
