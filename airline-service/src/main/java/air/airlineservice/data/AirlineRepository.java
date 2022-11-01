package air.airlineservice.data;

import air.airlineservice.service.airline.Airline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * An AirlineRepository abstracts a collection of Airline objects.
 */
@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {

    @Query("SELECT a FROM Airline a WHERE a.name = ?1")
    Optional<Airline> findByName(String name);
}
