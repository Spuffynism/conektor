package com.springmvc.model.parsing;

import org.junit.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.rules.ExpectedException;

public class FacebookMessageParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String trelloMessage = "trello -add test_card -list test_list";
    private String twitterMessage = "twitter -tweet \"this is a test tweet\"";
    private String emptyArgs = "trello";

    @Test
    public void testAppNameIsProperlyParsed() {
        FacebookMessageParser trelloParser = new FacebookMessageParser(trelloMessage);
        assertThat(trelloParser.getAppName()).isEqualTo("trello");

        FacebookMessageParser twitterParser = new FacebookMessageParser(twitterMessage);
        assertThat(twitterParser.getAppName()).isEqualTo("twitter");
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
        FacebookMessageParser parser = new FacebookMessageParser(trelloMessage);
        assertThat(parser.getArguments().size()).isEqualTo(2);

        assertThat(parser.getArguments().get("add")).isEqualTo("test_card");
        assertThat(parser.getArguments().get("list")).isEqualTo("test_list");
    }

    @Test
    public void testArgumentsInDoubleQuotesAreProperlyParsed() {
        FacebookMessageParser parser = new FacebookMessageParser(twitterMessage);
        assertThat(parser.getArguments().size()).isEqualTo(1);

        assertThat(parser.getArguments().get("tweet")).isEqualTo("this is a test tweet");
    }
}