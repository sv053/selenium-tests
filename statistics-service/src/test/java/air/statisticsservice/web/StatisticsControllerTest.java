package air.statisticsservice.web;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("category.IntegrationTest")
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldReturnOrderStatisticsOnOrderGetRequestWhenUserIsAdmin() throws Exception {
        getAndExpect(status().isOk());
    }

    private void getAndExpect(ResultMatcher status) throws Exception {
        mvc.perform(get("/statistics/order"))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldDenyAccessOnOrderGetRequestWhenUserIsUser() throws Exception {
        getAndExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void shouldDenyAccessOnOrderGetRequestWhenUserIsNotAuthenticated() throws Exception {
        getAndExpect(status().isUnauthorized());
    }
}
