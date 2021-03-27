package com.revature.exceptions;

/**
 * An exception that is called when trying to persist a resource to the database
 */
public class ResourcePersistenceException extends RuntimeException {

    public ResourcePersistenceException() {
        super("Resource not persisted");
    }

    public ResourcePersistenceException(String message) {
        super(message);
    }
}
