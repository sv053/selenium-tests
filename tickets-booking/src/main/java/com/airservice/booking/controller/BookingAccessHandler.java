package com.airservice.booking.controller;

import com.airservice.booking.model.Booking;
import com.airservice.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Optional;

public class BookingAccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(BookingAccessHandler.class);
    private final BookingService bookingService;

    @Autowired
    public BookingAccessHandler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Decides whether the current user can path the booking with the
     * specified booking ID.
     *
     * @param bookingID of the booking to patch
     *
     * @return true if access is available, false otherwise
     */
    public boolean canPatch(long bookingID) {
        try {
            Optional<Booking> booking = bookingService.findById(bookingID);
            if (booking.isEmpty()) {
                return false;
            } else {
                return hasAccess(booking.get().getUserId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    private boolean hasAccess(String bookingOwner) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return false;
        } else if (hasRole(authentication, "ADMIN")) {
            return true;
        } else {
            return authentication.getName().equals(bookingOwner);
        }
    }

    private boolean hasRole(Authentication authentication, String role) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }

    /**
     * Decides whether the current user can delete the booking with the
     * specified booking ID.
     *
     * @param bookingId of the booking to delete
     *
     * @return true if access is available, false otherwise
     */
    public boolean canDelete(long bookingId) {
        return canPatch(bookingId);
    }
}
