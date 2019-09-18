package xyz.ndlr.domain.parsing;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The equivalent of a parsed command line instruction
 */
public class ParsedMessage {
    private String command;
    private LinkedHashMap<String, String> arguments;
    private Map.Entry<String, String> firstArgument;

    public ParsedMessage(String command, LinkedHashMap<String, String> arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public String getFirstArgumentValue() {
        if (firstArgument == null) {
            firstArgument = arguments.entrySet().iterator().next();
        }

        return firstArgument.getValue();
    }
}
