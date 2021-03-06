package com.revature.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import com.revature.dtos.Credentials;
import com.revature.dtos.Principal;
import com.revature.entities.Asset;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.repositories.UserRepository;
import com.revature.util.JwtConfig;
import com.revature.util.JwtGenerator;
import com.revature.util.PasswordEncryption;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTest {

    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    private User theUser;

    @MockBean
    UserRepository userRepository;
    @Mock
    PasswordEncryption passwordEncryption;
    @MockBean
    JavaMailSender javaMailSender;


    @Autowired
    public UserControllerIntegrationTest(WebApplicationContext webContext) {
        this.webApplicationContext = webContext;
    }


    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        theUser = new User("nana","password","fakeEmail","first","last");
        theUser.setRole(UserRole.BASIC);
        theUser.setUserId(1);
    }

    @AfterAll
    public static void afterAllTest(){
        System.out.println("All Test finished!");
    }

    // Gives issues because the JavaMailSender is mocked, and needs to return an instance of a MimeMessage
    // on UserController.registerUser() - line 75
    @Test @Disabled
    public void registerUserWithValidData() throws Exception {
        User user1 = new User("nana","password","fakeEmail","first","last");
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
        User user1 = new User("nana","password","fakeEmail","first","last");
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
        when(userRepository.findUserByUsername(theUser.getUsername())).thenReturn(Optional.of(theUser));
        doNothing().when(userRepository).confirmedAccount(theUser.getUserId());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/confirmation/{username}",theUser.getUsername()))
                // Status should be 302 - redirect
                .andExpect(status().isFound());

    }

    @Test
    public void confirmUserAccountWithInvalidData() throws Exception {

        User fakeuser = new User();
        fakeuser.setUsername(" ");
        fakeuser.setUserId(10);
        fakeuser.setRole(UserRole.BASIC);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/confirmation/{username}",fakeuser.getUsername()))
                // Returned status code should be bad request
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginWithValidData() throws Exception {
        Credentials credentials = new Credentials("cspace","password");
        User user = new User();
        user.setUsername(credentials.getUsername());
        user.setPassword(credentials.getPassword());
        user.setEmail("cole.w.space@gmail.com");
        user.setFirstName("Cole");
        user.setLastName("Space");
        User hashedUser = new User(user);
        hashedUser.setAccountConfirmed(true);
        hashedUser.setPassword(PasswordEncryption.encryptString(hashedUser.getPassword()));

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(hashedUser));

        String Json = "{" +
                "\"username\":\"" + "cspace" + "\", " +
                "\"password\":\"" + "password" + "\"" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(Json)
        ).andExpect(status().is2xxSuccessful());


    }

    @Test
    public void loginWithInvalidData() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("agooge");
        user.setPassword("password123");
        user.setEmail("alexcgooge1@gmail.com");
        user.setFirstName("Alex");
        user.setLastName("Googe");
        org.mockito.Mockito.when(userRepository.findUserByUsername("agooge")).thenReturn(Optional.of(user));
        String Json = "{" +
                "\"username\":\"" + user.getUsername() + "\", " +
                "\"password\":\"" + "password1" + "\"" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllWithCorrectRole() throws Exception {
        User adminUser = new User("agooge","password","alexcgooge1@gmail.com","Alex","Googe");
        adminUser.setRole(UserRole.ADMIN);

        Principal principal = new Principal(adminUser);
        JwtGenerator generator = new JwtGenerator(new JwtConfig());
        String token = generator.generateJwt(principal);
        List<User> userList = new ArrayList<>();
        userList.add(adminUser);
        userList.add(theUser);

        when(userRepository.findAll()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .header("ASAP-token", token))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(userList.size()))
                // Ideally we also check the contents of the list, but it passed visual inspection
                .andDo(print());
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

    @Test
    public void getWatchlist_withValidUser_whereWatchlistIsNotEmpty() throws Exception {
        User basicUser = new User("agooge1","password","alexcgooge1@gmail.com","Alex","Googe");
        basicUser.setRole(UserRole.BASIC);
        Asset minAsset = new Asset();
        minAsset.setAssetId(1);
        minAsset.setName("min asset");
        minAsset.setTicker("MNAS");
        minAsset.setFinnhubIndustry("Fake");
        minAsset.setLastTouchedTimestamp(LocalDate.now());
        List<Asset> assetList = new ArrayList<>();
        assetList.add(minAsset);
        basicUser.setWatchlist(assetList);

        Principal principal = new Principal(basicUser);
        JwtGenerator generator = new JwtGenerator(new JwtConfig());
        String token = generator.generateJwt(principal);

        when(userRepository.findUserByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/watchlist")
            .header("ASAP-token", token))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(assetList.size()));

    }

    @Test
    public void getWatchlist_withValidUser_whereWatchlistIsEmpty() throws Exception {
        User basicUser = new User("agooge1","password","alexcgooge1@gmail.com","Alex","Googe");
        basicUser.setRole(UserRole.BASIC);
        List<Asset> assetList = Collections.emptyList();
        basicUser.setWatchlist(assetList);

        Principal principal = new Principal(basicUser);
        JwtGenerator generator = new JwtGenerator(new JwtConfig());
        String token = generator.generateJwt(principal);

        when(userRepository.findUserByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/watchlist")
            .header("ASAP-token", token))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(assetList.size()));

    }

    @Test
    public void getWatchlist_withInvalidUser() throws Exception {
        // Bad User
        User invalidUser = new User("ag","password","alexcgooge1@gmail.com","Alex","Googe");
        invalidUser.setRole(UserRole.BASIC);

        Principal principal = new Principal(invalidUser);
        JwtGenerator generator = new JwtGenerator(new JwtConfig());
        String token = generator.generateJwt(principal);

        when(userRepository.findUserByUsername(invalidUser.getUsername())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/watchlist")
                .header("ASAP-token", token))
                .andExpect(status().is4xxClientError());
    }
}
