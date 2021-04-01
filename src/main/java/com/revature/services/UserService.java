package com.revature.services;

import com.revature.entities.Asset;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.repositories.UserRepository;
import com.revature.util.PasswordEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User service class that has methods for calling the user repo and checking the validation of the data
 */
@Service
public class UserService {

    private UserRepository userRepository;

    /**
     * User service constructor that sets the user repository
     * @param userRepository user repository
     */
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Gets a user from the user controller. Checks to see if the user is valid.
     * Checks to see if the user is within the database. Then set the user's role to basic, encrypts the password,
     * set the account confirmed to false, and saves the user
     * @param newUser is the data containing the new user
     */
    public void registerUser(User newUser){
        if(!isUserValid(newUser)) throw new InvalidRequestException();

        if(userRepository.findUserByUsername(newUser.getUsername()).isPresent()){
            throw new ResourcePersistenceException();
        }
        newUser.setRole(UserRole.BASIC);

        // Uses BCrypt Password class to encrypt the password String before saving to the Database. . .
        newUser.setPassword(PasswordEncryption.encryptString(newUser.getPassword()));

        // Set to false since we want Users to confirm their email before logging in. . .
        newUser.setAccountConfirmed(false);
        userRepository.save(newUser);
    }

    /**
     * Gets the user id from the user controller. Checks to see if the integer value is less than and equals to zero.
     * Checks to see if the user exist. Then calls the confirmed account method from the repository
     * @param userId user's id
     */
    public void confirmAccount(int userId){
        if(userId<= 0){
            throw new InvalidRequestException();
        }

        if(!userRepository.findById(userId).isPresent()){
            throw new ResourceNotFoundException();
        }

        userRepository.confirmedAccount(userId);

    }

    /**
     * Gets the username from the user controller. Checks to see if the username is null or empty.
     * Then return the username by using the find user by username method from Repository
     * @param username is the username of the user
     * @return the user object
     */
    public User getUserByUsername(String username) {
        if (username == null || username.trim().equals("")) {
            throw new InvalidRequestException();
        }

        return userRepository.findUserByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Authenticates if a username and password corresponds to a User in the database
     * @param username the username provided to check
     * @param password the un-hashed password to check
     * @return a User object if the username and password match, null if no match
     */
    public User authenticate(String username, String password) {
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException();
        }

        User user = userRepository.findUserByUsername(username).orElseThrow(ResourceNotFoundException::new);

        // If the BCrypt hashed password does not match the password provided. . .
        if (!PasswordEncryption.verifyPassword(password, user.getPassword())) {
            throw new InvalidRequestException();
        }

        // If the User has not clicked the link in their email. . .
        if (!user.isAccountConfirmed()) {
            throw new InvalidRequestException("Account not validated");
        }

        return user;
    }

    /**
     * Gets all of the Users in the database
     * @return a List of User objects
     */
    public List<User> getallUsers(){

        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return users;
    }

    /**
     * Gets the list of Assets that are on a user's watchlist
     * @param username
     * @return a List of Asset objects
     */
    public List<Asset> getWatchlistFromUser(String username) {
        return userRepository
                .findUserByUsername(username)
                .orElseThrow(ResourceNotFoundException::new)
                .getWatchlist();
    }

    /**
     * Adds an {@code Asset} to a user's watchlist
     * @param username
     * @param asset
     */
    public void addToWatchlist(String username, Asset asset) {
        User u = userRepository
                .findUserByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        u.getWatchlist().add(asset);
        userRepository.save(u);
    }

    /**
     * Removes an {@code Asset} from a user's watchlist
     * @param username
     * @param asset
     */
    public void removeFromWatchlist(String username, Asset asset) {
        User u = userRepository
                    .findUserByUsername(username)
                    .orElseThrow(ResourceNotFoundException::new);
        u.getWatchlist().remove(asset);
        userRepository.save(u);
    }

    /**
     * helper function to check if the user is a valid user
     * @param user user
     * @return returns true if the user is valid else false
     */
    private Boolean isUserValid(User user){
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        return true;
    }


}
