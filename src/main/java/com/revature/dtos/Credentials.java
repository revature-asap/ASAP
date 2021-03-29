package com.revature.dtos;

/**
 * This class stores the username and password of an user
 */
public class Credentials {

    /**
     * The username of a user
     */
    private String username;

    /**
     * The password of a user
     */
    private String password;

    /**
     * Default Constructor
     */
    public Credentials() {
        super();
    }

    /**
     * Constructor that initialize the username and password
     * @param username is the username of the user
     * @param password is the password of the user
     */
    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
