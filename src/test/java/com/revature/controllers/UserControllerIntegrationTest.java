package com.revature.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.revature.dtos.Credentials;
import com.revature.dtos.Principal;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.repositories.UserRepository;
import com.revature.util.JwtConfig;
import com.revature.util.JwtGenerator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTest {

    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    private User theUser;

    @MockBean
    UserRepository userRepository;

    @Autowired
    public UserControllerIntegrationTest(WebApplicationContext webContext) {
        this.webApplicationContext = webContext;
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

    @Test @Disabled
    public void confirmUserAccountWithValidData() throws Exception {

        when(userRepository.findById(theUser.getUserId())).thenReturn(Optional.of(theUser));
        doNothing().when(userRepository).confirmedAccount(theUser.getUserId());

        // redirect
        mockMvc.perform(MockMvcRequestBuilders.get("/users/confirmation/{username}",theUser.getUsername()))
                .andExpect(status().is(302));

    }

    @Test @Disabled
    public void confirmUserAccountWithInvalidData() throws Exception {

        User fakeuser = new User();
        fakeuser.setUsername(" ");
        fakeuser.setUserId(10);
        fakeuser.setRole(UserRole.BASIC);

        // redirect
        mockMvc.perform(MockMvcRequestBuilders.get("/users/confirmation/{username}",fakeuser.getUsername()))
                .andExpect(status().is(302));

    }

    @Test @Disabled
    public void loginWithValidData() throws Exception {
        Credentials credentials = new Credentials("calvin123","password");
        User user = new User();
        user.setUsername(credentials.getUsername());
        user.setPassword(credentials.getPassword());
        user.setEmail("calvin123@yahoo.com");
        user.setFirstName("calvin");
        user.setLastName("zheng");
        when(userRepository.findUserByUsername("calvin123")).thenReturn(Optional.of(user));
        String Json = "{" +
                "\"username\":\"" + "calvin123" + "\", " +
                "\"password\":\"" + "password" + "\"" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(Json)
        ).andExpect(status().is2xxSuccessful());


    }

    @Test @Disabled
    public void loginWithInvalidData() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("agooge");
        user.setPassword("password123");
        user.setEmail("alexcgooge1@gmail.com");
        user.setFirstName("Alex");
        user.setLastName("Googe");
        when(userRepository.findUserByUsername("agooge")).thenReturn(Optional.of(user));
        String Json = "{" +
                "\"username\":\"" + user.getUsername() + "\", " +
                "\"password\":\"" + "password1" + "\"" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json)
        ).andExpect(status().is4xxClientError());
    }

    @Test @Disabled
    public void getAllWithCorrectRole() throws Exception {

        User adminUser = new User("agooge","password","alexcgooge1@gmail.com","Alex","Googe");
        adminUser.setRole(UserRole.ADMIN);

        Principal principal = new Principal(adminUser);

        JwtGenerator generator = new JwtGenerator(new JwtConfig());

        String token = generator.generateJwt(principal);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .header("ASAP-token", token))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getAllWithWrongRole() throws Exception {
        User basicUser = new User("agooge","password","alexcgooge1@gmail.com","Alex","Googe");
        basicUser.setRole(UserRole.BASIC);

        Principal principal = new Principal(basicUser);

        JwtGenerator generator = new JwtGenerator(new JwtConfig());

        String token = generator.generateJwt(principal);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .header("ASAP-token", token))
                .andExpect(status().is4xxClientError());
    }



}
