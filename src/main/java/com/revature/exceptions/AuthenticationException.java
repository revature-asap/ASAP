package com.revature.exceptions;

/**
 * An exception that gets called when a user attempts to access an endpoint that requires a user be logged in
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("Authentication Failed! Please log in before trying to proceed.");
    }

    public AuthenticationException(String message) {
        super(message);
    }

}
