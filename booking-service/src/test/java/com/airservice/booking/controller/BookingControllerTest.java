package com.airservice.booking.controller;

import com.airservice.booking.model.Booking;
import com.airservice.booking.service.BookingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("category.IntegrationTest")
@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    private static String newBookingJson;
    private static String minDateTime;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingService bookingService;

    @BeforeAll
    private static void init() {
        minDateTime = "2015-08-02T00:29:53.949";
        newBookingJson = "{" +
                "\"userId\": \"123456789\"," +
                "\"ticketId\": \"EK122\", " +
                "\"dateTime\": \"" +
                minDateTime + "\" }";
       }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldReturnCreatedNewBooking_success() throws Exception {
        mockMvc.perform(post("/booking")
                        .content(newBookingJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldReturnAllFoundBookings_success() throws Exception {
        mockMvc.perform(post("/booking")
                .content(newBookingJson));
        mockMvc.perform(get("/booking"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithAnonymousUser
    void shouldDenyReturnAllFoundBookings_failure() throws Exception {
        mockMvc.perform(get("/booking"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    void shouldDenyReturnBookingFoundById_failure() throws Exception {
        Booking booking = bookingService.createBooking(new Booking.Builder("user635")
                .ticketId("EK128")
                .bookingDateTime(LocalDateTime.MIN)
                .build());
        mockMvc.perform(get("/booking/"+booking.getId()))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldReturnBookingFoundById_success() throws Exception {
        Booking booking = bookingService.createBooking(new Booking.Builder("user635")
                .ticketId("EK128")
                .bookingDateTime(LocalDateTime.MIN)
                .build());
        mockMvc.perform(get("/booking/"+booking.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithAnonymousUser
    void shouldDenyDeleteBooking_failure() throws Exception {
        mockMvc.perform(delete("/booking/6"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldDeleteBookingRoleAdmin_success() throws Exception {
        mockMvc.perform(delete("/booking/4"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

