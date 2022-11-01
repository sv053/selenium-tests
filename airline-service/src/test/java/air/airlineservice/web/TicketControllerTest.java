package air.airlineservice.web;

import air.airlineservice.service.ticket.Ticket;
import air.airlineservice.service.ticket.TicketService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("category.IntegrationTest")
@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {
    private static String newTicket1Json;
    private static String newTicket2Json;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TicketService ticketService;

    @BeforeAll
    public static void createNewFlightJson() {
        newTicket1Json = "{" +
                "\"flight\":{ " +
                    "\"id\": \"1\"" +
                "}," +
                "\"price\": \"100\"," +
                "\"luggageAllowed\": \"true\"" +
                "}";

        newTicket2Json = "{" +
                "\"flight\":{ " +
                    "\"id\": \"2\"" +
                "}," +
                "\"price\": \"200\"," +
                "\"luggageAllowed\": \"true\"" +
                "}";
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnTicketsOnTicketsGetRequest() throws Exception {
        mvc.perform(get("/tickets"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnTicketsOnTicketsGetByFlightRequest() throws Exception {
        mvc.perform(get("/tickets?flight=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnTicketsOnTicketsGetByFlightAndPriceRequest() throws Exception {
        mvc.perform(get("/tickets?flight=1&price=100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnTicketsOnTicketsGetByFlightWithLuggageRequest() throws Exception {
        mvc.perform(get("/tickets?flight=1&luggageAllowed=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnTicketByIdOnTicketGetRequest() throws Exception {
        mvc.perform(get("/tickets/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldReturnSavedTicketOnTicketsPostRequestWhenUserIsAdmin() throws Exception {
        postAndExpect(newTicket1Json, status().isCreated());
    }

    public void postAndExpect(String data, ResultMatcher status) throws Exception {
        mvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "owner2", authorities = "USER")
    public void shouldReturnSavedTicketOnTicketsPostRequestWhenUserIsResourceOwner() throws Exception {
        postAndExpect(newTicket2Json, status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldDeleteTicketOnTicketDeleteRequestWhenUserIsAdmin() throws Exception {
        deleteByIdAndExpect(3L, status().isNoContent());

        Optional<Ticket> deleted = ticketService.findById(3L);
        assertThat(deleted, is(Optional.empty()));
    }

    private void deleteByIdAndExpect(long id, ResultMatcher status) throws Exception {
        mvc.perform(delete("/tickets/" + id))
                .andDo(print())
                .andExpect(status);
    }

    @Test
    @WithMockUser(authorities = "USER", username = "owner4")
    public void shouldDeleteTicketOnTicketDeleteRequestWhenUserISResourceOwner() throws Exception {
        deleteByIdAndExpect(4L, status().isNoContent());

        Optional<Ticket> deleted = ticketService.findById(4L);
        assertThat(deleted, is(Optional.empty()));
    }

    @Test
    @WithMockUser(authorities = "USER", username = "owner")
    public void shouldDenyTicketDeletionWhenUserIsNotResourceOwner() throws Exception {
        deleteByIdAndExpect(4L, status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void shouldDenyTicketDeletionWhenUserIsNotAuthenticated() throws Exception {
        deleteByIdAndExpect(4L, status().isUnauthorized());
    }
}