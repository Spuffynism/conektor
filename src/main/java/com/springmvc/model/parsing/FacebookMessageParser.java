package com.springmvc.model.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacebookMessageParser {
    private static Pattern appNamePattern = Pattern.compile("^([a-zA-Z]+){1}");
    private static Pattern argumentsPattern =
            Pattern.compile("-([a-zA-Z-]+){1}\\s([^\"\\s]+|\"[^\"]+\"){1}");
    private String message;
    private String appName;
    private Map<String, String> arguments;

    public FacebookMessageParser(String message) throws IllegalArgumentException {
        this.message = message;

        this.appName = tryGetAppName();
        this.arguments = tryGetArguments();
    }

    private String tryGetAppName() throws IllegalArgumentException {
        Matcher matcher = appNamePattern.matcher(message);
        String appName = parseAppname(matcher);

        if (appName == null || appName.isEmpty())
            throw new IllegalArgumentException("invalid app name");

        return appName;
    }

    private String parseAppname(Matcher matcher) {
        String appName = null;

        while (matcher.find())
            appName = matcher.group(1);

        return appName;
    }

    private Map<String, String> tryGetArguments() throws IllegalArgumentException {
        Matcher matcher = argumentsPattern.matcher(message);
        Map<String, String> arguments = parseArguments(matcher);

        if (arguments.isEmpty())
            throw new IllegalArgumentException("no arguments");

        return arguments;
    }

    private Map<String, String> parseArguments(Matcher matcher)
            throws StringIndexOutOfBoundsException {
        HashMap<String, String> arguments = new HashMap<>();

        String argument, value;
        while (matcher.find()) {
            argument = matcher.group(1);
            value = deserializeArgumentValue(matcher.group(2));

            arguments.put(argument, value);
        }

        return arguments;
    }

    private String deserializeArgumentValue(String argumentValue)
            throws StringIndexOutOfBoundsException {
        if (argumentValue.startsWith("\"") && argumentValue.endsWith("\""))
            argumentValue = argumentValue.substring(1, argumentValue.length() - 1);

        return argumentValue;
    }

    ///<editor-fold> Basic getters and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, String> arguments) {
        this.arguments = arguments;
    }

    ///</editor-fold>
}
