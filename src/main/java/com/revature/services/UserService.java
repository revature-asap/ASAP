package com.revature.services;

import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.repositories.UserRepository;
import com.revature.util.PasswordEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void registerUser(User newUser){
        if(!isUserValid(newUser)) throw new InvalidRequestException();

        if(userRepository.findUserByUsername(newUser.getUsername()).isPresent()){
            throw new ResourcePersistenceException();
        }
        newUser.setRole(UserRole.BASIC);
        newUser.setPassword(PasswordEncryption.encryptString(newUser.getPassword()));
        newUser.setAccountConfirmed(false);
        userRepository.save(newUser);
    }

    public void confirmAccount(int userId){
        if(userId<= 0){
            throw new InvalidRequestException();
        }

        if(!userRepository.findById(userId).isPresent()){
            throw new ResourceNotFoundException();
        }

        userRepository.confirmedAccount(userId);

    }

    public User getUserByUsername(String username) {
        if (username == null || username.trim().equals("")) {
            throw new InvalidRequestException();
        }

        return userRepository.findUserByUsername(username).orElseThrow(ResourceNotFoundException::new);
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
