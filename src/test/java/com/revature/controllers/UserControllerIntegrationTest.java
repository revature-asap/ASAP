package com.revature.controllers;

import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTest {

    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    private User theUser;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;



    @Autowired
    public UserControllerIntegrationTest(WebApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        theUser = new User("nana","password","nana123@yahoo.com","first","last");
        theUser.setRole(UserRole.BASIC);
        theUser.setUserId(1);

    }

    @AfterAll
    public static void afterAllTest(){
        System.out.println("All Test finished!");
    }

    // @Test
    // public void registerUserWithValidData() throws Exception {
    //     User user1 = new User("nana","password","nana123@yahoo.com","first","last");
    //     user1.setRole(UserRole.BASIC);
    //     when(userRepository.save(user1)).thenReturn(null);

    //     String Json = "{" +
    //             "\"username\":\"" + user1.getUsername() + "\", " +
    //             "\"password\":\"" + user1.getPassword() + "\", " +
    //             "\"email\":\"" + user1.getEmail() + "\", " +
    //             "\"firstName\":\"" + user1.getFirstName() + "\", " +
    //             "\"lastName\":\"" + user1.getLastName() + "\", " +
    //             "\"role\":\"" + user1.getRole().toString().toUpperCase() + "\"" +
    //             "}";

    //     mockMvc.perform(MockMvcRequestBuilders.post("/users")
    //             .contentType(MediaType.APPLICATION_JSON_VALUE)
    //             .content(Json))
    //             .andExpect(status().isCreated());

    // }

    @Test
    public void registerUserWithInvalidData() throws Exception {
        User user1 = new User("nana","password","nana123@yahoo.com","first","last");
        user1.setRole(UserRole.BASIC);
        when(userRepository.save(user1)).thenReturn(null);

        String Json = "{" +
                "\"username\":\"" + user1.getUsername() + "\", " +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Json))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void confirmUserAccountWithValidData() throws Exception {

        when(userRepository.findById(theUser.getUserId())).thenReturn(Optional.of(theUser));
        when(userService.getUserByUsername(theUser.getUsername())).thenReturn(theUser);
        doNothing().when(userRepository).confirmedAccount(theUser.getUserId());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/confirmation/{username}",theUser.getUsername()))
                .andExpect(status().is(204));

    }

    @Test
    public void confirmUserAccountWithInvalidData() throws Exception {

        User fakeuser = new User();
        fakeuser.setUsername(" ");
        fakeuser.setUserId(10);
        fakeuser.setRole(UserRole.BASIC);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/confirmation/{username}",fakeuser.getUsername()))
                .andExpect(status().isBadRequest());

    }
}
