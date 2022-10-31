package air.airlineservice.web;

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
                "\"flightId\": \"1\"," +
                "\"price\": \"100\"" +
                "}";

        newTicket2Json = "{" +
                "\"flightId\": \"2\"," +
                "\"price\": \"200\"" +
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
}