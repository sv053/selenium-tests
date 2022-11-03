package com.airservice.booking.service;

import com.airservice.booking.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class BookingServiceTest {

    private Booking booking;
    @Autowired
    BookingService bookingService;
    @BeforeEach
    void init() {
        booking = new Booking.Builder("user542")
                .flightId("EK128")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
    }

    @Test
    void findById_Success_BookingIsFound() {
        Booking foundBooking = bookingService.findBookingById(booking.getId());

        assertEquals(booking, foundBooking);
    }

    @Test
    void findAllBookings_Success_BookingsAreFound() {
        List<Booking> foundBooking = bookingService.findAllBookings();

        assertEquals(booking, foundBooking);
    }

    @Test
    void updateBooking_Success_BookingIsUpdated() {
        Booking updatedBooking = new Booking.Builder("user635")
                .flightId("EK128")
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
                .flightId("SU128")
                .id(17l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        Booking foundBooking = bookingService.createBooking(booking);
        Booking persistentBooking = bookingService.findBookingById(bookingToCreate.getId());
        assertEquals(bookingToCreate, persistentBooking);
    }

    @Test
    void removeBooking_Success_BookingIsRemoved() {
        Booking booking = new Booking.Builder("user777")
                .flightId("QH128")
                .id(17l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        bookingService.createBooking(booking);
        Booking foundBooking = bookingService.findBookingById(booking.getId());
        bookingService.removeBooking(booking.getId());
        assertEquals(null, bookingService.findBookingById(booking.getId()));
    }
}

