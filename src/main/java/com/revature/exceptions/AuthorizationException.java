package com.revature.exceptions;

/**
 * An exception that gets called when a user attempts to access an unauthorized endpoint
 */
public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("Unathorized to Access Requested Endpoint!");
    }

    public AuthorizationException(String message) {
        super(message);
    }

}
