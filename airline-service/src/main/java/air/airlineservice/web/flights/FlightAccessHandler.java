package air.airlineservice.web.flights;

import air.airlineservice.service.flight.Flight;
import air.airlineservice.service.flight.FlightService;
import air.airlineservice.web.airline.AirlineAccessHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FlightAccessHandler {
    private static final Logger logger = LogManager.getLogger(FlightAccessHandler.class);

    private final FlightService flightService;
    private final AirlineAccessHandler accessHandler;

    @Autowired
    public FlightAccessHandler(FlightService flightService,
                               AirlineAccessHandler accessHandler) {
        this.flightService = flightService;
        this.accessHandler = accessHandler;
    }

    /**
     * Decides whether the current user can post the specified flight.
     *
     * @param flight flight to post
     *
     * @return true if access is available, false otherwise
     */
    public boolean canPost(Flight flight) {
        long airlineId = flight.getAirline().getId();
        return accessHandler.canPatch(airlineId);
    }

    /**
     * Decides whether the current user can patch the specified flight.
     *
     * @param flight flight to patch
     *
     * @return true if access is available, false otherwise
     */
    public boolean canPatch(Flight flight) {
        return canPost(flight);
    }

    /**
     * Decides whether the current user can delete the flight with the
     * specified ID.
     *
     * @param id ID of the flight to delete
     *
     * @return true if access is available, false otherwise
     */
    public boolean canDelete(long id) {
        try {
            Optional<Flight> flight = flightService.findById(id);
            if (flight.isEmpty()) {
                return false;
            } else {
                return canPost(flight.get());
            }
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }
}
