package xyz.ndlr;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import xyz.ndlr.security.auth.AccountCredentials;

import javax.servlet.Filter;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class AuthenticationTest {

    private static final String VALID_USER_USERNAME = "user";
    private static final String VALID_USER_PASSOWRD = "user";
    private static final String INVALID_USER_PASSOWRD = "invalid";

    private static final String LOGIN_PATH = "/auth/login";

    private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Autowired
    void setConverters(HttpMessageConverter<Object>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Test
    public void loginWithValidCredentialsReturnsValidToken() throws Exception {
        AccountCredentials validCredentials = new AccountCredentials(VALID_USER_USERNAME,
                VALID_USER_PASSOWRD);

        mockMvc.perform(post(LOGIN_PATH)
                .content(json(validCredentials))
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andExpect(content().contentType("application/json"));
        //TODO Check that jwt token is valid
    }

    @Test
    public void loginWithNoIdentifierFieldIsUnauthorized() throws Exception {
        String noIdentifier = String.format("{\"password\":\"%s\"}", VALID_USER_PASSOWRD);

        loginIsUnauthorizedAndBodyIsEmpty(noIdentifier);
    }

    @Test
    public void loginWithEmptyIdentifierIsUnauthorized() throws Exception {
        AccountCredentials emptyIdentifier =
                new AccountCredentials("", VALID_USER_PASSOWRD);

        loginIsUnauthorizedAndBodyIsEmpty(json(emptyIdentifier));
    }

    @Test
    public void loginWithNoPasswordFieldIsUnauthorized() throws Exception {
        String noPassword = String.format("{\"identifier\":\"%s\"}", VALID_USER_USERNAME);

        loginIsUnauthorizedAndBodyIsEmpty(noPassword);
    }

    @Test
    public void loginWithEmptyPasswordIsUnauthorized() throws Exception {
        AccountCredentials emptyPassword = new AccountCredentials(VALID_USER_USERNAME, "");

        loginIsUnauthorizedAndBodyIsEmpty(json(emptyPassword));
    }

    @Test
    public void loginWithInvalidPasswordIsUnauthorized() throws Exception {
        AccountCredentials invalidPassword = new AccountCredentials(VALID_USER_USERNAME,
                INVALID_USER_PASSOWRD);

        loginIsUnauthorizedAndBodyIsEmpty(json(invalidPassword));
    }

    private void loginIsUnauthorizedAndBodyIsEmpty(String content) throws Exception {
        mockMvc.perform(post(LOGIN_PATH)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/json"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(isEmptyString()));
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o,
                MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
