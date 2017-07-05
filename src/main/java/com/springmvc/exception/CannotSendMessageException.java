package com.springmvc.exception;

public class CannotSendMessageException extends Exception {
    public CannotSendMessageException(String message) {
        super(message);
    }
}
