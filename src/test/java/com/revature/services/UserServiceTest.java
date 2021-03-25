package com.revature.services;


import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

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
        user1.setRole(UserRole.BASIC);

    }

    @Test
    public void registerUserTest(){
        when(userRepository.save(user1)).thenReturn(user1);

        userService.registerUser(user1);

        verify(userRepository, times(1)).save(user1);
    }

    @Test(expected = InvalidRequestException.class)
    public void registerNulUserTest(){

        userService.registerUser(nullUser);

        verify(userRepository, times(0)).save(nullUser);
    }

    @Test(expected = ResourcePersistenceException.class)
    public void registerExistUserTest(){

        when(userRepository.findUserByUsername(user1.getUsername())).thenReturn(Optional.of(user1));

        userService.registerUser(user1);

        verify(userRepository, times(0)).save(user1);
    }


}
