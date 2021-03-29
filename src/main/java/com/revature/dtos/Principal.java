package com.revature.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.entities.User;
import com.revature.entities.UserRole;

/**
 * DTO class that represents the id, username, and role of a user
 */
public class Principal {

    /**
     * The JWT value for the user
     */
    @JsonIgnore
    private String token;

    /**
     * User id in the database
     */
    private int id;

    /**
     * The username of the User
     */
    private String username;

    /**
     * The ENUM role of the user
     */
    private UserRole role;

    /**
     * No args Constructor
     */
    public Principal() {
        super();
    }

    /**
     * Constructor from a User object
     * @param user the User to create a Principal for
     */
    public Principal(User user) {
        this.id = user.getUserId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }

    /**
     * All args Constructor
     * @param id the id of the User
     * @param username the username of the User
     * @param role the ENUM role of the User
     */
    public Principal(int id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
