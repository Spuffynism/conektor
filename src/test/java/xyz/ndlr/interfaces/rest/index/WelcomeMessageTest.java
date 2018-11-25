package xyz.ndlr.interfaces.rest.index;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WelcomeMessageTest {

    @Test
    public void testTimestamp() {
        WelcomeMessage welcome = new WelcomeMessage(null);
        assertThat(welcome.getTimestamp()).isNotNull();

        long randomTimestamp = (long) (Math.random() * Long.MAX_VALUE);
        welcome.setTimestamp(randomTimestamp);
        assertThat(welcome.getTimestamp()).isEqualTo(randomTimestamp);
    }

    @Test
    public void testMessage() {
        String emptyString = "";
        WelcomeMessage welcome = new WelcomeMessage(emptyString);
        assertThat(welcome.getMessage()).isEqualTo(emptyString);

        welcome = new WelcomeMessage(null);
        assertThat(welcome.getMessage()).isEqualTo(null);

        String welcomeMessage = "this is conektor";
        welcome = new WelcomeMessage(welcomeMessage);
        assertThat(welcome.getMessage()).isEqualTo(welcomeMessage);
    }
}
