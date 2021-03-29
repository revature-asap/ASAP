package com.revature.controllers;


import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.services.EmailService;
import com.revature.services.UserService;
import com.revature.util.JwtGenerator;
import com.revature.dtos.Credentials;
import com.revature.dtos.Principal;
import com.revature.util.JwtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * This class is used to access user methods by hitting the end points
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private EmailService emailService;
    private final JwtGenerator jwtGenerator;
    private JwtParser jwtparser;
    /**
     * Constructor for auto wiring User Service and Email Service
     * @param userService service class for the users
     * @param emailService service class for email
     */
    @Autowired
    public UserController(UserService userService, EmailService emailService, JwtGenerator jwtGenerator, JwtParser jwtparser){
        this.userService = userService;
        this.emailService = emailService;
        this.jwtGenerator = jwtGenerator;
        this.jwtparser = jwtparser;
    }

    //Post

    /**
     * Post method that will create a row in the database and also send an email
     * to the user's private email
     * @param newUser the json object of user data
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody User newUser){
        userService.registerUser(newUser);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newUser.getEmail());
        mailMessage.setSubject("Complete Registration For ASAP");
        mailMessage.setFrom("asap.revature@yahoo.com");

        // Need to change link when the API is hosted. . .
        mailMessage.setText("To confirm your account, please click here: "
        + "http://localhost:5000/users/confirmation/" + newUser.getUsername());

        emailService.sendEmail(mailMessage);
    }

    /**
     * Get method that will update the confirmed account column
     * @param username the username of the user
     */
    @GetMapping(path = "/confirmation/{username}")
    public void confirmUserAccount(@PathVariable String username, HttpServletResponse response) {
        User user = userService.getUserByUsername(username);
        if(user != null){
            userService.confirmAccount(user.getUserId());
            response.setStatus(204);
        }
        else{
            response.setStatus(400);
        }

    }

    /**
     * Post method that takes in the credentials from the user which contains username and password. User Service then authenticate
     * the input username and password. If the user is not equal to null, we create a principal and generate a JWT token and set it to ASAP-token
     * After set the response status to 201. If the user is equal to null, set response status to 401
     * @param credentials contains username and password
     * @param response is the Http Servlet Response
     * @return The principal of the user which contains id , username , and role
     */
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Principal loginUser(@RequestBody @Valid Credentials credentials, HttpServletResponse response) {

        User user = userService.authenticate(credentials.getUsername(), credentials.getPassword());

        if (user != null) {
            Principal principal = new Principal(user);

            String token = jwtGenerator.generateJwt(principal);
            principal.setToken(token);

            response.addHeader("ASAP-token", principal.getToken());
            response.setStatus(201);

            return principal;
        }

        response.setStatus(401);
        return null;
    }

    /**
     * Get method that will output the list of users. It will only return the list if the user is an Admin from the token.
     * If the user isn't an Admin, it will set the response status to 403 and return null.
     * @param request is the Http Servlet Request
     * @param response is the Http Servlet response
     * @return the list of users in the database
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(HttpServletRequest request,HttpServletResponse response){
        String token = jwtparser.getTokenFromHeader(request);
        Principal user = jwtparser.parseToken(token);

        if(user.getRole() == UserRole.ADMIN){
            response.setStatus(200);
            return userService.getallUsers();

        }
            response.setStatus(403);
            return null;
    }



}
