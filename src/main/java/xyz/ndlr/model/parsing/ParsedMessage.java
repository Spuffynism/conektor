package xyz.ndlr.model.parsing;

import java.util.Map;

/**
 * The equivalent of a parsed command line instruction
 */
public class ParsedMessage {
    private String command;
    private Map<String, String> arguments;

    public ParsedMessage(String command, Map<String, String> arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, String> arguments) {
        this.arguments = arguments;
    }
}
