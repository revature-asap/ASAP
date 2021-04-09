package com.revature.exceptions;

/**
 * An exception that gets called when an invalid request happens
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
        super("Invalid request made!");
    }

    public InvalidRequestException(String message) {
        super(message);
    }

}
