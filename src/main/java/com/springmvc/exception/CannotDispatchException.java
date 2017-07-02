package com.springmvc.exception;

public class CannotDispatchException extends Exception {
    public CannotDispatchException() {
    }

    public CannotDispatchException(String message) {
        super(message);
    }
}
