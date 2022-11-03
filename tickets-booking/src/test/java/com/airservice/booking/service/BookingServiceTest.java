package com.airservice.booking.service;

import com.airservice.booking.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("category.IntegrationTest")
@SpringBootTest
@Transactional
class BookingServiceTest {

    private Booking booking;
    @Autowired
    BookingService bookingService;
    @BeforeEach
    void init() {
        booking = bookingService.createBooking(new Booking.Builder("user542")
                .ticketId("EK128")
                .bookingDateTime(LocalDateTime.MIN)
                .build());
    }

    @Test
    void findById_Success_BookingIsFound() {
        Booking foundBooking = bookingService.findBookingById(booking.getId());
        assertEquals(booking, foundBooking);
    }

    @Test
    void findAllBookings_Success_BookingsAreFound() {
        List<Booking> foundBooking = bookingService.findAllBookings();
        assertTrue(foundBooking.contains(booking));
    }

    @Test
    void updateBooking_Success_BookingIsUpdated() {
        Booking updatedBooking = new Booking.Builder("user635")
                .ticketId("EK128")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        booking = bookingService.updateBooking(updatedBooking);
        Booking persistentBooking = bookingService.findBookingById(updatedBooking.getId());
        assertEquals(updatedBooking, persistentBooking);
    }

    @Test
    void createBooking_Success_BookingIsCreated() {
        Booking bookingToCreate = new Booking.Builder("user888")
                .ticketId("SU128")
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        Booking bookingCreated = bookingService.createBooking(booking);
        assertTrue(bookingService.findAllBookings().contains(bookingCreated));
    }

    @Test
    void removeBooking_Success_BookingIsRemoved() {
        List<Booking> bookings = bookingService.findAllBookings();
        Booking booking = bookingService.findBookingById(bookings.stream().findFirst().get().getId());
        Booking bookingToCompare = bookingService.findBookingById(booking.getId());
        assertEquals(booking, bookingToCompare);
    }
}

