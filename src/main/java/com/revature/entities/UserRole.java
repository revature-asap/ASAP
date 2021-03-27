package com.revature.entities;

/**
 * This is the user roles model
 */
public enum UserRole {

    BASIC("Basic"), ADMIN("Admin"), DEV("Dev");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

}
