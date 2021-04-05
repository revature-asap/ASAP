package com.revature.services;


import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.repositories.UserRepository;
import com.revature.util.PasswordEncryption;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    User user1;
    User user2;
    User nullUser;
    List<User> listUsers;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;



    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        nullUser = new User();
        user1 = new User("nana","password","nana123@yahoo.com","first","last");
        user1.setUserId(1);
        user1.setRole(UserRole.BASIC);
        user2 = new User("nana","password","nana123@yahoo.com","first","last");
        user2.setUserId(2);
        user2.setRole(UserRole.BASIC);
        listUsers = new ArrayList<>();
        listUsers.add(user1);
        listUsers.add(user2);
    }

    @Test
    public void registerUserTest(){
        when(userRepository.save(user1)).thenReturn(user1);

        userService.registerUser(user1);

        verify(userRepository, times(1)).save(user1);
    }

    @Test(expected = InvalidRequestException.class)
    public void registerNullUserTest(){

        userService.registerUser(nullUser);

        verify(userRepository, times(0)).save(nullUser);
    }

    @Test(expected = ResourcePersistenceException.class)
    public void registerExistUserTest(){

        when(userRepository.findUserByUsername(user1.getUsername())).thenReturn(Optional.of(user1));

        userService.registerUser(user1);

        verify(userRepository, times(0)).save(user1);
    }

    @Test
    public void confirmAccountTest(){

        when(userRepository.findById(user1.getUserId())).thenReturn(Optional.of(user1));

        doNothing().when(userRepository).confirmedAccount(user1.getUserId());

        userService.confirmAccount(user1.getUserId());

        verify(userRepository, times(1)).confirmedAccount(user1.getUserId());
    }

    @Test(expected = InvalidRequestException.class)
    public void confirmAccountIfUserIdIsZEROTest(){

        user1.setUserId(0);

        userService.confirmAccount(user1.getUserId());

        verify(userRepository, times(0)).confirmedAccount(user1.getUserId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void confirmAccountIfUserNotExistTest(){

        userService.confirmAccount(user1.getUserId());

        verify(userRepository, times(1)).confirmedAccount(user1.getUserId());
    }

    @Test
    public void getUserByUsername(){

        when(userRepository.findUserByUsername(user1.getUsername())).thenReturn(Optional.of(user1));

        User existUser = userService.getUserByUsername(user1.getUsername());

        assertEquals(user1.getUsername(),existUser.getUsername());
    }

    @Test(expected = InvalidRequestException.class)
    public void getUserByUsernameIfUserNameIsNull(){

        user1.setUsername(null);

        User existUser = userService.getUserByUsername(user1.getUsername());

        assertEquals(user1.getUsername(),existUser.getUsername());
    }

    @Test(expected = InvalidRequestException.class)
    public void getUserByUsernameIfUserNameIsEmpty(){

        user1.setUsername("");

        User existUser = userService.getUserByUsername(user1.getUsername());

        assertEquals(user1.getUsername(),existUser.getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getUserByUsernameIfUserNotExist(){

        User existUser = userService.getUserByUsername(user1.getUsername());

        assertEquals(user1.getUsername(),existUser.getUsername());
    }

    @Test
    public void authenticateIfValid(){
        User hashUser = new User();
        hashUser.setUsername(user1.getUsername());
        hashUser.setPassword(PasswordEncryption.encryptString(user1.getPassword()));
        hashUser.setAccountConfirmed(true);
        when(userRepository.findUserByUsername(user1.getUsername())).thenReturn(Optional.of(hashUser));

        User existUser = userService.authenticate(user1.getUsername(),user1.getPassword());
        assertEquals(existUser.getUsername(),user1.getUsername());

    }

    @Test(expected = InvalidRequestException.class)
    public void authenticateIfBadUsername(){
        User hashUser = new User();
        hashUser.setUsername(user1.getUsername());
        hashUser.setPassword(PasswordEncryption.encryptString(user1.getPassword()));
        hashUser.setAccountConfirmed(true);
        user1.setUsername(" ");

        userService.authenticate(user1.getUsername(),user1.getPassword());
    }

    @Test(expected = InvalidRequestException.class)
    public void authenticateIfBadPassword(){
        User hashUser = new User();
        hashUser.setUsername(user1.getUsername());
        hashUser.setPassword(user1.getPassword());
        hashUser.setAccountConfirmed(true);
        when(userRepository.findUserByUsername(user1.getUsername())).thenReturn(Optional.of(hashUser));

        userService.authenticate(user1.getUsername(),user1.getPassword());
    }

    @Test(expected = InvalidRequestException.class)
    public void authenticateIfAccountNotConfirmed(){
        User hashUser = new User();
        hashUser.setUsername(user1.getUsername());
        hashUser.setPassword(PasswordEncryption.encryptString(user1.getPassword()));
        when(userRepository.findUserByUsername(user1.getUsername())).thenReturn(Optional.of(hashUser));

        userService.authenticate(user1.getUsername(),user1.getPassword());
    }

    @Test
    public void getAllUsers(){

        when(userRepository.findAll()).thenReturn(listUsers);
        List<User> users = userService.getallUsers();
        assertEquals(users,listUsers);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAllUsersIfEmpty(){

        when(userRepository.findAll()).thenReturn(Lists.emptyList());
        userService.getallUsers();
    }

    @Test
    public void testGetWatchlistFromUser_withValidUser() {

        when(userRepository.save(user1)).thenReturn(null);
    }

}
