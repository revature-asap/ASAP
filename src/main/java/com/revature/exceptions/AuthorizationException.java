package com.revature.exceptions;

/**
 * An exception that gets called when a logged in user attempts to access an unauthorized endpoint
 */
public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("User is Unauthorized to Access Requested Endpoint!");
    }

    public AuthorizationException(String message) {
        super(message);
    }

}
