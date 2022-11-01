package air.userservice.web;

import air.userservice.service.User;
import air.userservice.service.UserService;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("category.IntegrationTest")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private static String newUserJson;
    private static String updatedUser1Json;
    private static String updatedUser2Json;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @BeforeAll
    public static void createNewUserJson() {
        newUserJson = "{" +
                "\"email\": \"emailnew@gmail.com\"," +
                "\"password\": \"123456789\"," +
                "\"name\": \"Alexander\"," +
                "\"nationality\": \"nationality\"," +
                "\"passportNumber\": \"sdgh66g7\"," +
                "\"age\": \"18\"" +
                "}";
    }

    @BeforeAll
    private static void createUpdatedUserJsons() {
        updatedUser1Json = "{" +
                "\"email\": \"email@gmail.com\"," +
                "\"password\": \"987654321\"," +
                "\"name\": \"Mark\"," +
                "\"nationality\": \"nationality2\"," +
                "\"passportNumber\": \"gh668909\"," +
                "\"age\": \"22\"" +
                "}";

        updatedUser2Json = "{" +
                "\"email\": \"email@gmail.com\"," +
                "\"password\": \"fkm4454\"," +
                "\"name\": \"Mark\"," +
                "\"nationality\": \"nationality3\"," +
                "\"passportNumber\": \"1234fght\"," +
                "\"age\": \"40\"" +
                "}";
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldReturnUserOnUserGetByEmailRequestWhenUserIsAdmin() throws Exception {
        getByEmailAndExpect(status().isOk());
    }

    private void getByEmailAndExpect(ResultMatcher status) throws Exception {
        mvc.perform(get("/users").param("email", "email@gmail.com"))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = "USER", username = "email@gmail.com")
    public void shouldReturnUserOnUserGetByEmailRequestWhenUserIsResourceOwner() throws Exception {
        getByEmailAndExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER", username = "email2@gmail.com")
    public void shouldDenyAccessToUserWhenUserIsNotResourceOwner() throws Exception {
        getByEmailAndExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void shouldDenyAccessToUserWhenUserIsNotAuthenticated() throws Exception {
        getByEmailAndExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnSavedUserOnUsersPostRequest() throws Exception {
        postAndExpect(newUserJson, status().isCreated());
    }

    private void postAndExpect(String data, ResultMatcher status) throws Exception {
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldReturnUpdatedUserOnUsersPatchRequestWhenUserIsAdmin() throws Exception {
        User initial = userService.findByEmail("email2@gmail.com").orElseThrow();
        patchByEmailAndExpect("email2@gmail.com", updatedUser1Json, status().isOk());

        User updated = userService.findByEmail("email2@gmail.com").orElseThrow();
        assertThat(updated, is(not(equalTo(initial))));
    }

    private void patchByEmailAndExpect(String email, String data, ResultMatcher status) throws Exception {
        mvc.perform(patch("/users")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "email3@gmail.com", authorities = "USER")
    public void shouldReturnUpdatedUserOnUsersPatchRequestWhenUserIsResourceOwner() throws Exception {
        User initial = userService.findByEmail("email3@gmail.com").orElseThrow();
        patchByEmailAndExpect("email3@gmail.com", updatedUser2Json, status().isOk());

        User updated = userService.findByEmail("email3@gmail.com").orElseThrow();
        assertThat(updated, is(not(equalTo(initial))));
    }

    @Test
    @WithMockUser(username = "email2@gmail.com", authorities = "USER")
    public void shouldDenyUserPatchingWhenUserIsNotResourceOwner() throws Exception {
        patchByEmailAndExpect("email@gmail.com", updatedUser1Json, status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void shouldDenyUserPatchingWhenUserIsNotAuthenticated() throws Exception {
        patchByEmailAndExpect("email@gmail.com", updatedUser1Json, status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldDeleteUserOnUserDeleteRequestWhenUserIsAdmin() throws Exception {
        deleteByEmailAndExpect("email4@gmail.com", status().isNoContent());

        Optional<User> deleted = userService.findByEmail("email4@gmail.com");
        assertThat(deleted, is(Optional.empty()));
    }

    private void deleteByEmailAndExpect(String email, ResultMatcher status) throws Exception {
        mvc.perform(delete("/users").param("email", email))
                .andDo(print())
                .andExpect(status);
    }

    @Test
    @WithMockUser(username = "email5@gmail.com", authorities = "USER")
    public void shouldDeleteUserOnUserDeleteRequestWhenUserIsResourceOwner() throws Exception {
        deleteByEmailAndExpect("email5@gmail.com", status().isNoContent());

        Optional<User> deleted = userService.findByEmail("email5@gmail.com");
        assertThat(deleted, is(Optional.empty()));
    }

    @Test
    @WithMockUser(username = "email@gmail.com", authorities = "USER")
    public void shouldDenyUserDeletionWhenUserIsNotResourceOwner() throws Exception {
        deleteByEmailAndExpect("email5@gmail.com", status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void shouldDenyUserDeletionWhenUserIsNotAuthenticated() throws Exception {
        deleteByEmailAndExpect("email5@gmail.com", status().isUnauthorized());
    }
}
