package xyz.ndlr.domain.parsing;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String trelloMessage = "trello -add test_card -list test_list";
    private String twitterMessage = "twitter -tweet \"this is a test tweet\"";
    private String emptyArgs = "trello";

    @Test
    public void appNameIsProperlyParsed() {
        MessageParser trelloParser = new MessageParser(trelloMessage);
        assertThat(trelloParser.getCommand()).isEqualTo("trello");

        MessageParser twitterParser = new MessageParser(twitterMessage);
        assertThat(twitterParser.getCommand()).isEqualTo("twitter");
    }

    @Test
    public void throwsExceptionWhenMessageNull() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("a message must be present");

        String nullString = null;
        new MessageParser(nullString);
    }

    @Test
    public void throwsExceptionWhenMessageEmpty() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("a message must be present");

        new MessageParser("");
    }

    @Test
    public void throwsExceptionWhenAppNameNotPresent() {
        String emptyAppName = "-add cardX -list listX";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("no command");

        new MessageParser(emptyAppName);
    }

    @Test
    public void argumentsAreProperlyParsed() {
        MessageParser parser = new MessageParser(trelloMessage);
        assertThat(parser.getArguments().size()).isEqualTo(2);

        assertThat(parser.getArguments().get("add")).isEqualTo("test_card");
        assertThat(parser.getArguments().get("list")).isEqualTo("test_list");
    }

    @Test
    public void argumentsInDoubleQuotesAreProperlyParsed() {
        MessageParser parser = new MessageParser(twitterMessage);
        assertThat(parser.getArguments().size()).isEqualTo(1);

        assertThat(parser.getArguments().get("tweet")).isEqualTo("this is a test tweet");
    }
}