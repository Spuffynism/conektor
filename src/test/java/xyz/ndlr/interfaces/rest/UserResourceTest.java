package xyz.ndlr.interfaces.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import xyz.ndlr.ControllerTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest
@RunWith(SpringRunner.class)
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @WithUserDetails("admin")
    public void controllerProducesJsonUTF8() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    @WithUserDetails("admin")
    public void whenAdminGetAllUsersReturnsAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")));
    }

    @Test
    @WithUserDetails("user")
    public void whenNotAdminGetAllUsersIsUnauthorized() throws Exception {
        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(isEmptyString()));
    }

    @Test
    @WithUserDetails("admin")
    public void whenAdminGetUserByIdReturnsUser() throws Exception {
        int existingUserId = 3;

        getById(existingUserId, status().isOk(), jsonPath("id", is(existingUserId)));
    }

    @Test
    @WithUserDetails("user")
    public void whenIdIsMeGetUserByIdReturnsMe() throws Exception {
        int myId = 2;

        getById(myId, status().isOk(), jsonPath("id", is(myId)));
    }

    @Test
    @WithUserDetails("user")
    public void whenIdIsNotMeGetUserByIdIsUnauthorized() throws Exception {
        int notMyId = 1;

        getById(notMyId, status().isUnauthorized(), content().string(isEmptyString()));
    }

    @Test
    @WithUserDetails("admin")
    public void whenIdDoesNotExistGetUserByIdIsNotFound() throws Exception {
        int nonExistingId = Integer.MAX_VALUE;

        getById(nonExistingId, status().isNotFound(), content().string(isEmptyString()));
    }

    private void getById(int id, ResultMatcher expectedStatus, ResultMatcher expectedContent)
            throws Exception {
        mockMvc.perform(get("/users/" + id))
                .andExpect(expectedStatus)
                .andExpect(expectedContent);
    }

    @Test
    @WithUserDetails("admin")
    public void whenAdminGetMeReturnsMe() throws Exception {
        int myId = 1;
        mockMvc.perform(get("/users/me")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(myId)));
    }

    @Test
    @WithUserDetails("user")
    public void whenUserGetMeReturnsMe() throws Exception {
        int myId = 2;
        mockMvc.perform(get("/users/me")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(myId)));
    }

    @Test
    @WithUserDetails("user")
    //TODO
    public void createUserReturnsUser() throws Exception {
    }

    @Test
    @WithUserDetails("user")
    public void updateUserIsNotImplemented() throws Exception {
        int id = 1;
        mockMvc.perform(put("/users/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotImplemented())
                .andExpect(content().string(isEmptyString()));
    }

    @Test
    @WithUserDetails("admin")
    //TODO
    public void whenAdminDeleteUserDeletesUser() throws Exception {

    }

    @Test
    @WithUserDetails("admin")
    public void whenUserDoesNotExistDeleteUserIsNotFound() throws Exception {
        int nonExistingId = Integer.MAX_VALUE;
        mockMvc.perform(delete("/users/" + nonExistingId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().string(isEmptyString()));
    }

    @Test
    @WithUserDetails("user")
    public void whenNotAdminDeleteUserIsUnauthorized() throws Exception {
        int myId = 1;
        mockMvc.perform(delete("/users/" + myId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(isEmptyString()));
    }
}