package xyz.ndlr.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import xyz.ndlr.Application;
import xyz.ndlr.UserDetailsServiceTestConfiguration;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UserDetailsServiceTestConfiguration.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @WithUserDetails("admin")
    public void whenAdminGetAllUsersReturnsAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
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
        mockMvc.perform(get("/users/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(expectedStatus)
                .andExpect(expectedContent);
    }
}