package xyz.ndlr.presentation.rest.index;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xyz.ndlr.ControllerITest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerITest
@RunWith(SpringRunner.class)
public class IndexResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void indexShowsWelcomeMessage() throws Exception {
        mockMvc.perform(get("/")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("this is conektor")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}
