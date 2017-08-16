package xyz.ndlr.model.parsing;

import xyz.ndlr.model.provider.facebook.webhook.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
    private static Pattern commandPattern = Pattern.compile("^([a-zA-Z]+){1}");
    private static Pattern argumentsPattern =
            Pattern.compile("-([a-zA-Z-]+){1}\\s([^\"\\s]+|\"[^\"]+\"){1}");
    private String message;
    private String command;
    private Map<String, String> arguments;

    MessageParser(String message) throws IllegalArgumentException {
        if (message == null || message.isEmpty())
            throw new IllegalArgumentException("a message must be present");

        this.message = message;

        this.setCommand(message);
        this.setArguments(message);
    }

    public MessageParser(Message message) {
        this(message.getText());
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
        Map<String, String> arguments = parseArguments(matcher);

        if (arguments.isEmpty())
            throw new IllegalArgumentException("no arguments provided\n. Message was : " + message);

        this.arguments = arguments;
    }

    private Map<String, String> parseArguments(Matcher matcher)
            throws StringIndexOutOfBoundsException {
        Map<String, String> arguments = new HashMap<>();

        String argument, value;
        while (matcher.find()) {
            argument = matcher.group(1);
            value = deserializeArgumentValue(matcher.group(2));

            arguments.put(argument, value);
        }

        return arguments;
    }

    /**
     * TODO Maybe add other deserializing steps?
     *
     * @param argumentValue the argument's value to deserialize
     * @return the cleansed argument value
     * @throws StringIndexOutOfBoundsException when the argument's too small
     */
    private String deserializeArgumentValue(String argumentValue)
            throws StringIndexOutOfBoundsException {
        // Strips out nesting double quotes
        if (argumentValue.startsWith("\"") && argumentValue.endsWith("\""))
            argumentValue = trimStartAndEndChar(argumentValue);

        if (argumentValue.startsWith("'") && argumentValue.endsWith("'"))
            argumentValue = trimStartAndEndChar(argumentValue);

        return argumentValue;
    }

    private String trimStartAndEndChar(String value) {
        return value.substring(1, value.length() - 1);
    }

    ///<editor-fold> Basic getters and setters

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

    ///</editor-fold>
}
