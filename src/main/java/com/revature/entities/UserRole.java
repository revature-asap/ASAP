package com.revature.entities;

public enum UserRole {

    BASIC("Basic"), ADMIN("Admin"), DEV("Dev");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

}
