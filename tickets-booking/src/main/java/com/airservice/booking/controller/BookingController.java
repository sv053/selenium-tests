package com.airservice.booking.controller;

import com.airservice.booking.model.Booking;
import com.airservice.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    Logger logger = LoggerFactory.getLogger(BookingController.class);

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') and #returnObject.userId == authentication.name or hasAuthority('ADMIN')")
    public Booking findBookingById(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Booking> findAllBookings() {
        return bookingService.findAllBookings();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER') and #booking.userId == authentication.name or hasAuthority('ADMIN')")
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER') and #id == authentication.name or hasAuthority('ADMIN')")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.removeBooking(id);
    }
}

