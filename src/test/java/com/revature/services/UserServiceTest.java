package com.revature.services;


import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    User user1;
    User nullUser;

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
}
