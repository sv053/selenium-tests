package com.airservice.booking.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Tag("category.UnitTest")
class BookingTest {

    @Test
    void hashCode_sameIdsSameLogins_True() {
        Booking booking1 = new Booking.Builder("user12")
                .ticketId("SU587")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        Booking booking2 = new Booking.Builder("user12")
                .ticketId("SU587")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        assertEquals(booking1, booking2);
    }

    @Test
    void hashCode_differentIdsSameLogins_False() {
        Booking booking1 = new Booking.Builder("user12")
                .ticketId("SU587")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        Booking booking2 = new Booking.Builder("lou27")
                .ticketId("SU587")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        assertNotEquals(booking1, booking2);
    }

    @Test
    void equals_sameIdsSameLogins_True() {
        Booking booking1 = new Booking.Builder("user12")
                .ticketId("SU587")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        Booking booking2 = new Booking.Builder("user12")
                .ticketId("SU587")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        assertEquals(booking1, booking2);
    }

    @Test
    void equals_differentIdsSameLogins_False() {
        Booking booking1 = new Booking.Builder("rio43")
                .ticketId("SU587")
                .id(85l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        Booking booking2 = new Booking.Builder("louri87")
                .ticketId("SU587")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        assertNotEquals(booking1, booking2);
    }

    @Test
    void equals_DifferentFlights_True() {
        Booking booking1 = new Booking.Builder("user12")
                .ticketId("S7887")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        Booking booking2 = new Booking.Builder("user12")
                .ticketId("SU587")
                .id(15l)
                .bookingDateTime(LocalDateTime.MIN)
                .build();
        assertNotEquals(booking1, booking2);
    }
}

