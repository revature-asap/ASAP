package com.revature.controllers;

import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.repositories.UserRepository;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTest {

    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;


    @Autowired
    public UserControllerIntegrationTest(WebApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


    }

    @AfterAll
    public static void afterAllTest(){
        System.out.println("All Test finished!");
    }

    @Test
    public void registerUserWithValidData() throws Exception {
        User user1 = new User("nana","password","nana123@yahoo.com","first","last");
        user1.setRole(UserRole.BASIC);
        when(userRepository.save(user1)).thenReturn(null);

        String Json = "{" +
                "\"username\":\"" + user1.getUsername() + "\", " +
                "\"password\":\"" + user1.getPassword() + "\", " +
                "\"email\":\"" + user1.getEmail() + "\", " +
                "\"firstName\":\"" + user1.getFirstName() + "\", " +
                "\"lastName\":\"" + user1.getLastName() + "\", " +
                "\"role\":\"" + user1.getRole().toString().toUpperCase() + "\"" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Json))
                .andExpect(status().isCreated());

    }

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
}
