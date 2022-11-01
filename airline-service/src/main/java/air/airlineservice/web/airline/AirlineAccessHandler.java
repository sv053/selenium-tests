package air.airlineservice.web.airline;

import air.airlineservice.service.airline.Airline;
import air.airlineservice.service.airline.AirlineService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class AirlineAccessHandler {
    private static final Logger logger = LogManager.getLogger(AirlineAccessHandler.class);
    private final AirlineService airlineService;

    @Autowired
    public AirlineAccessHandler(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    /**
     * Decides whether the current user can path the airline with the
     * specified airline ID.
     *
     * @param airlineId ID of the airline to patch
     *
     * @return true if access is available, false otherwise
     */
    public boolean canPatch(long airlineId) {
        try {
            Optional<Airline> airline = airlineService.findById(airlineId);
            if (airline.isEmpty()) {
                return false;
            } else {
                return hasAccess(airline.get().getOwner());
            }
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    private boolean hasAccess(String airlineOwner) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return false;
        } else if (hasRole(authentication, "ADMIN")) {
            return true;
        } else {
            return authentication.getName().equals(airlineOwner);
        }
    }

    private boolean hasRole(Authentication authentication, String role) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }

    /**
     * Decides whether the current user can delete the airline with the
     * specified airline ID.
     *
     * @param airlineId ID of the airline to delete
     *
     * @return true if access is available, false otherwise
     */
    public boolean canDelete(long airlineId) {
        return canPatch(airlineId);
    }
}
