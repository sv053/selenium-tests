package com.airservice.booking.controller;

import com.airservice.booking.service.BookingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    private static String newBookingJson;
    private static String newBookingJsonToCheckDelete;
    private static String minDateTime;
    private static String maxDateTime;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingService bookingService;

    @BeforeAll
    private static void init() {
        minDateTime = LocalDateTime.MIN.toString();
        maxDateTime = LocalDateTime.MAX.toString();
        newBookingJson = "{" +
                "\"id\": \"18976\"," +
                "\"userId\": \"123456789\"," +
                "\"flightId\": \"EK122\"" +
                "\"booking_datetime\": \"" +
                minDateTime + "\" }";
        newBookingJsonToCheckDelete = "{" +
                "\"id\": \"9462\"," +
                "\"userId\": \"123456789\"," +
                "\"flightId\": \"EK122\"" +
                "\"booking_datetime\": \"" +
                maxDateTime + "\" }";
    }

    @Test
    void shouldReturnCreatedNewBooking() throws Exception {
        mockMvc.perform(post("/")
                        .content(newBookingJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnAllFoundBookings() throws Exception {
        mockMvc.perform(post("/")
                .content(newBookingJson));
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnBookingFoundById() throws Exception {
        mockMvc.perform(get("/user1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void postAndExpect(String data, ResultMatcher status) throws Exception {
        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteBooking() throws Exception {
        mockMvc.perform((post("/")
                        .content(newBookingJsonToCheckDelete)))
                .andDo(print()).andExpect(status().isOk());
        mockMvc.perform(delete("/booking").param("booking", newBookingJsonToCheckDelete))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

