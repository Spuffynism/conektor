package com.springmvc.model.parsing;

import org.junit.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.rules.ExpectedException;

public class FacebookMessageParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String trelloMessage = "trello -add cardX -list listX";
    private String twitterMessage = "twitter -tweet \"this is a test tweet\"";
    private String emptyArgs = "trello";

    @Test
    public void testAppNameIsProperlyParsed() {
        FacebookMessageParser trelloParser = new FacebookMessageParser(trelloMessage);
        assertThat("trello".equals(trelloParser.getAppName()));

        FacebookMessageParser twitterParser = new FacebookMessageParser(twitterMessage);
        assertThat("twitter".equals(twitterParser.getAppName()));
    }

    @Test
    public void testThrowsExceptionWhenMessageNull() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("message cannot be null or empty");

        new FacebookMessageParser(null);
    }

    @Test
    public void testThrowsExceptionWhenMessageEmpty() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("message cannot be null or empty");

        new FacebookMessageParser("");
    }

    @Test
    public void testThrowsExceptionWhenAppNameNotPresent() {
        String emptyAppName = "-add cardX -list listX";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid app name");

        new FacebookMessageParser(emptyAppName);
    }

    @Test
    public void testArgumentsAreProperlyParsed() {

    }
}