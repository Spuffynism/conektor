package xyz.ndlr.domain.parsing;

import xyz.ndlr.domain.provider.facebook.webhook.event.Message;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
    private static final Pattern commandPattern = Pattern.compile("^([a-zA-Z]+)");
    private static Pattern argumentsPattern =
            Pattern.compile("-([a-zA-Z-]+)\\s([^\"\\s]+|\"[^\"]+\")");
    private String message;
    private String command;
    private LinkedHashMap<String, String> arguments;

    MessageParser(String message) throws IllegalArgumentException {
        if (message == null || message.isEmpty())
            throw new IllegalArgumentException("a message must be present");

        this.message = message;

        this.setCommand(message);
        this.setArguments(message);
    }

    public static MessageParser fromMessage(Message message) throws IllegalArgumentException {
        String text;
        boolean messageIsQuickReply = message.getQuickReply() != null;
        if (messageIsQuickReply) {
            text = message.getQuickReply().getPayload();
        } else {
            text = message.getText();
        }

        return new MessageParser(text);
    }

    private void setCommand(String message) throws IllegalArgumentException {
        Matcher matcher = commandPattern.matcher(message);
        String command = parseCommand(matcher);

        if (command == null || command.isEmpty())
            throw new IllegalArgumentException("no command");

        this.command = command;
    }

    private String parseCommand(Matcher matcher) {
        String command = null;

        while (matcher.find())
            command = matcher.group(1);

        return command;
    }

    private void setArguments(String message) throws IllegalArgumentException {
        Matcher matcher = argumentsPattern.matcher(message);

        this.arguments = parseArguments(matcher);
    }

    /**
     * From a matcher, return pairings of arguments and their values.
     *
     * @param matcher
     * @return a map of arguments and their values
     * @throws StringIndexOutOfBoundsException
     */
    private LinkedHashMap<String, String> parseArguments(Matcher matcher)
            throws StringIndexOutOfBoundsException {
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();

        String argument, value;
        while (matcher.find()) {
            argument = matcher.group(1);
            value = removeNestingQuotes(matcher.group(2));

            arguments.put(argument, value);
        }

        return arguments;
    }

    /**
     * Removes nesting quotes around a string.
     *
     * @param value that which will be cleansed
     * @return the cleansed value
     * @throws StringIndexOutOfBoundsException when the argument's too small
     */
    private String removeNestingQuotes(String value)
            throws StringIndexOutOfBoundsException {
        boolean nestedByQuotes = (value.startsWith("\"") && value.endsWith("\""))
                || (value.startsWith("'") && value.endsWith("'"));
        if (nestedByQuotes) {
            value = trimStartAndEndChar(value);
        }

        return value;
    }

    private String trimStartAndEndChar(String value) {
        return value.substring(1, value.length() - 1);
    }

    public ParsedMessage getParsedMessage() {
        return new ParsedMessage(command, arguments);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommand() {
        return command;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }
}
