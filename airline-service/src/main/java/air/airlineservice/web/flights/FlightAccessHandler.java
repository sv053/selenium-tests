package air.airlineservice.web.flights;

import air.airlineservice.web.airline.AirlineAccessHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightAccessHandler {
    private final AirlineAccessHandler accessHandler;

    @Autowired
    public FlightAccessHandler(AirlineAccessHandler accessHandler) {
        this.accessHandler = accessHandler;
    }

    /**
     * Decides whether the current user can post the flight with the
     * specified airline ID.
     *
     * @param airlineId ID of the airline of the flight to post
     *
     * @return true if access is available, false otherwise
     */
    public boolean canPost(long airlineId) {
        return accessHandler.canPatch(airlineId);
    }

    /**
     * Decides whether the current user can patch the flight with the
     * specified airline ID.
     *
     * @param airlineId ID of the airline of the flight to post
     *
     * @return true if access is available, false otherwise
     */
    public boolean canPatch(long airlineId) {
        return canPost(airlineId);
    }

    /**
     * Decides whether the current user can delete the flight with the
     * specified airline ID.
     *
     * @param airlineId ID of the airline of the flight to delete
     *
     * @return true if access is available, false otherwise
     */
    public boolean canDelete(long airlineId) {
        return canPost(airlineId);
    }
}
