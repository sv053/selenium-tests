package com.airservice.booking.controller;

import com.airservice.booking.model.Booking;
import com.airservice.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public Booking findBookingById(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

    @GetMapping
    public List<Booking> findAllBookings() {
        return bookingService.findAllBookings();
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @DeleteMapping
    public void deleteBooking(@PathVariable Long id) {
        bookingService.removeBooking(id);
    }
}

