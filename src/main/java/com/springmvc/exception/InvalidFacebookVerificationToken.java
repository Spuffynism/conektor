package com.springmvc.exception;

public class InvalidFacebookVerificationToken extends Exception {
    public InvalidFacebookVerificationToken(String message) {
        super(message);
    }
}