package com.revature.entities;

/**
 * This is the user roles model
 */
public enum UserRole {

    /**
     * The roles of a user
     */
    BASIC("Basic"), ADMIN("Admin"), DEV("Dev");

    private String name;

    /**
     * Constructor for initializing the name of the role
     * @param name is the name of the user role
     */
    UserRole(String name) {
        this.name = name;
    }

}
