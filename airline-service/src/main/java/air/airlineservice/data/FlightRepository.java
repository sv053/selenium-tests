package air.airlineservice.data;

import air.airlineservice.service.flight.Flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A FlightRepository abstracts a collection of Flight objects.
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.airline.id = ?1")
    List<Flight> findAllByAirlineId(long airlineId);
}
