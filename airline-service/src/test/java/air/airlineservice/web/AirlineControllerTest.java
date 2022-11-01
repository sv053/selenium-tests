package air.airlineservice.web;

import air.airlineservice.service.airline.Airline;
import air.airlineservice.service.airline.AirlineService;

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
public class AirlineControllerTest {
    private static String newAirlineJson;
    private static String updatedAirline1Json;
    private static String updatedAirline2Json;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AirlineService airlineService;

    @BeforeAll
    public static void createNewUserJson() {
        newAirlineJson = "{" +
                "\"name\": \"new-name\"," +
                "\"description\": \"new-description\"," +
                "\"owner\": \"new-owner\"," +
                "\"image\": { " +
                    "\"data\": \"fkfewflfk\"" +
                "}" +
                "}";
    }

    @BeforeAll
    private static void createUpdatedUserJsons() {
        updatedAirline1Json = "{" +
                "\"name\": \"updated-name\"," +
                "\"description\": \"updated-description\"," +
                "\"owner\": \"updated-owner\"," +
                "\"image\": { " +
                "\"data\": \"fweffffer3r3r4\"" +
                "}" +
                "}";

        updatedAirline2Json = "{" +
                "\"name\": \"updated-name2\"," +
                "\"description\": \"updated-description2\"," +
                "\"owner\": \"owner2\"," +
                "\"image\": { " +
                "\"data\": \"weflmfu2rijk\"" +
                "}" +
                "}";
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnAirlinesOnAirlinesGetRequest() throws Exception {
        mvc.perform(get("/airlines"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnAirlineByIdOnAirlinesGetRequest() throws Exception {
        mvc.perform(get("/airlines/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnAirlineByNameOnAirlinesGetRequest() throws Exception {
        mvc.perform(get("/airlines?name=name4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "new-owner", authorities = "USER")
    public void shouldReturnSavedAirlineOnAirlinesPostRequest() throws Exception {
        mvc.perform(post("/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAirlineJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldReturnUpdatedAirlineOnAirlinePatchRequestWhenUserIsAdmin() throws Exception {
        Airline initial = airlineService.findById(1L).orElseThrow();
        patchByIdAndExpect(1L, updatedAirline1Json, status().isOk());

        Airline updated = airlineService.findById(1L).orElseThrow();
        assertThat(updated, is(not(equalTo(initial))));
    }

    private void patchByIdAndExpect(Long id, String data, ResultMatcher status) throws Exception {
        mvc.perform(patch("/airlines/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "owner2", authorities = "USER")
    public void shouldReturnUpdatedAirlineOnAirlinesPatchRequestWhenUserIsResourceOwner() throws Exception {
        Airline initial = airlineService.findById(2L).orElseThrow();
        patchByIdAndExpect(2L, updatedAirline2Json, status().isOk());

        Airline updated = airlineService.findById(2L).orElseThrow();
        assertThat(updated, is(not(equalTo(initial))));
    }

    @Test
    @WithMockUser(username = "owner", authorities = "USER")
    public void shouldDenyAirlinePatchingWhenUserIsNotResourceOwner() throws Exception {
        patchByIdAndExpect(1L, updatedAirline1Json, status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void shouldDenyAirlinePatchingWhenUserIsNotAuthenticated() throws Exception {
        patchByIdAndExpect(1L, updatedAirline1Json, status().isUnauthorized());
    }
}
