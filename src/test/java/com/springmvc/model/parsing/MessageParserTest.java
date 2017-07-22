package com.springmvc.model.parsing;

import org.junit.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.rules.ExpectedException;

public class MessageParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String trelloMessage = "trello -add test_card -list test_list";
    private String twitterMessage = "twitter -tweet \"this is a test tweet\"";
    private String emptyArgs = "trello";

    @Test
    public void testAppNameIsProperlyParsed() {
        MessageParser trelloParser = new MessageParser(trelloMessage);
        assertThat(trelloParser.getCommand()).isEqualTo("trello");

        MessageParser twitterParser = new MessageParser(twitterMessage);
        assertThat(twitterParser.getCommand()).isEqualTo("twitter");
    }

    @Test
    public void testThrowsExceptionWhenMessageNull() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("a message must be present");

        String nullString = null;
        new MessageParser(nullString);
    }

    @Test
    public void testThrowsExceptionWhenMessageEmpty() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("a message must be present");

        new MessageParser("");
    }

    @Test
    public void testThrowsExceptionWhenAppNameNotPresent() {
        String emptyAppName = "-add cardX -list listX";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("no command");

        new MessageParser(emptyAppName);
    }

    @Test
    public void testArgumentsAreProperlyParsed() {
        MessageParser parser = new MessageParser(trelloMessage);
        assertThat(parser.getArguments().size()).isEqualTo(2);

        assertThat(parser.getArguments().get("add")).isEqualTo("test_card");
        assertThat(parser.getArguments().get("list")).isEqualTo("test_list");
    }

    @Test
    public void testArgumentsInDoubleQuotesAreProperlyParsed() {
        MessageParser parser = new MessageParser(twitterMessage);
        assertThat(parser.getArguments().size()).isEqualTo(1);

        assertThat(parser.getArguments().get("tweet")).isEqualTo("this is a test tweet");
    }
}