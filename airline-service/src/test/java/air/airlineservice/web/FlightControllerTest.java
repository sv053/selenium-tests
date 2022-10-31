package air.airlineservice.web;

import air.airlineservice.service.flight.Flight;
import air.airlineservice.service.flight.FlightService;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("category.IntegrationTest")
@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {
    private static String newFlight1Json;
    private static String newFlight2Json;
    private static String updatedFlight1Json;
    private static String updatedFlight2Json;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FlightService flightService;

    @BeforeAll
    public static void createNewFlightJson() {
        newFlight1Json = "{" +
                "\"airline\": {" +
                    "\"id\":\"1\"" +
                "}," +
                "\"from\": {" +
                    "\"country\": \"country\", " +
                    "\"airport\": \"airport\", " +
                    "\"gate\": \"1\"" +
                "}, " +
                "\"to\": {" +
                    "\"country\": \"country\", " +
                    "\"airport\": \"airport\", " +
                    "\"gate\": \"1\"" +
                "}, " +
                "\"dateTime\": \"2022-10-31T21:51:36.867100600\"" +
                "}";

        newFlight2Json = "{" +
                "\"airline\": {" +
                    "\"id\":\"2\"" +
                "}," +
                "\"from\": {" +
                    "\"country\": \"country\", " +
                    "\"airport\": \"airport\", " +
                    "\"gate\": \"1\"" +
                "}, " +
                "\"to\": {" +
                    "\"country\": \"country\", " +
                    "\"airport\": \"airport\", " +
                    "\"gate\": \"1\"" +
                "}, " +
                "\"dateTime\": \"2022-10-31T21:51:36.867100600\"" +
                "}";
    }

    @BeforeAll
    private static void createUpdatedFlightJsons() {
        updatedFlight1Json = "{" +
                "\"airline\": {" +
                    "\"id\":\"2\"" +
                "}," +
                "\"from\": {" +
                    "\"country\": \"updated-country\", " +
                    "\"airport\": \"updated-airport\", " +
                    "\"gate\": \"2\"" +
                "}, " +
                "\"to\": {" +
                    "\"country\": \"updated-country\", " +
                    "\"airport\": \"updated-airport\", " +
                    "\"gate\": \"3\"" +
                "}, " +
                "\"dateTime\": \"2022-10-31T21:51:36.867100600\"" +
                "}";

        updatedFlight2Json = "{" +
                "\"airline\": {" +
                    "\"id\":\"3\"" +
                "}," +
                "\"from\": {" +
                    "\"country\": \"updated-country2\", " +
                    "\"airport\": \"updated-airport2\", " +
                    "\"gate\": \"7\"" +
                "}, " +
                "\"to\": {" +
                    "\"country\": \"updated-country2\", " +
                    "\"airport\": \"updated-airport2\", " +
                    "\"gate\": \"10\"" +
                "}, " +
                "\"dateTime\": \"2022-10-31T21:51:36.867100600\"" +
                "}";
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnFlightsOnFlightsGetRequest() throws Exception {
        mvc.perform(get("/flights"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnFlightByIdOnFlightGetRequest() throws Exception {
        mvc.perform(get("/flights/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldReturnSavedFlightOnFlightsPostRequestWhenUserIsAdmin() throws Exception {
        postAndExpect(newFlight1Json, status().isCreated());
    }

    public void postAndExpect(String data, ResultMatcher status) throws Exception {
        mvc.perform(post("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "owner2", authorities = "USER")
    public void shouldReturnSavedFlightOnFlightsPostRequestWhenUserIsResourceOwner() throws Exception {
        postAndExpect(newFlight2Json, status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldReturnUpdatedFlightOnFlightPatchRequestWhenUserIsAdmin() throws Exception {
        Flight initial = flightService.findById(2L).orElseThrow();
        patchByIdAndExpect(2L, updatedFlight1Json, status().isOk());

        Flight updated = flightService.findById(2L).orElseThrow();
        assertThat(updated, is(not(equalTo(initial))));
    }

    private void patchByIdAndExpect(Long id, String data, ResultMatcher status) throws Exception {
        mvc.perform(patch("/flights/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "owner3", authorities = "USER")
    public void shouldReturnUpdatedFlightOnFlightPatchRequestWhenUserIsResourceOwner() throws Exception {
        Flight initial = flightService.findById(3L).orElseThrow();
        patchByIdAndExpect(3L, updatedFlight2Json, status().isOk());

        Flight updated = flightService.findById(3L).orElseThrow();
        assertThat(updated, is(not(equalTo(initial))));
    }

    @Test
    @WithMockUser(username = "owner", authorities = "USER")
    public void shouldDenyFlightPatchingWhenUserIsNotResourceOwner() throws Exception {
        patchByIdAndExpect(1L, updatedFlight1Json, status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void shouldDenyFlightPatchingWhenUserIsNotAuthenticated() throws Exception {
        patchByIdAndExpect(1L, updatedFlight1Json, status().isUnauthorized());
    }
}
