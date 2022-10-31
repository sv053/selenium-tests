package air.airlineservice.web.ticket;

import air.airlineservice.service.flight.Flight;
import air.airlineservice.service.flight.FlightService;
import air.airlineservice.service.ticket.Ticket;
import air.airlineservice.web.flights.FlightAccessHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TicketAccessHandler {
    private static final Logger logger = LogManager.getLogger(TicketAccessHandler.class);

    private final FlightService flightService;
    private final FlightAccessHandler accessHandler;

    @Autowired
    public TicketAccessHandler(FlightService flightService,
                               FlightAccessHandler accessHandler) {
        this.flightService = flightService;
        this.accessHandler = accessHandler;
    }

    /**
     * Decides whether the current user can post the specified ticket.
     *
     * @param ticket ticket to post
     *
     * @return true if access is available, false otherwise
     */
    public boolean canPost(Ticket ticket) {
        long flightId = ticket.getFlight().getId();
        Optional<Flight> flight = flightService.findById(flightId);
        if (flight.isEmpty()) {
            return false;
        } else {
            long airlineId = flight.get().getAirline().getId();
            return accessHandler.canPost(airlineId);
        }
    }
}
