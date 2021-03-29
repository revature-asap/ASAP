package com.revature.exceptions;

/**
 * An exception that gets called when a user attempts to access an unauthorized endpoint
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("Unathorized!");
    }

    public AuthenticationException(String message) {
        super(message);
    }

}
