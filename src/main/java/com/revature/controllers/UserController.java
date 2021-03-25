package com.revature.controllers;


import com.revature.entities.User;
import com.revature.services.EmailService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private EmailService emailService;

    @Autowired
    public UserController(UserService userService,EmailService emailService){
        this.userService = userService;
        this.emailService = emailService;
    }

    //Post
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void registerUser(@RequestBody User newUser){
        userService.registerUser(newUser);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newUser.getEmail());
        mailMessage.setSubject("Complete Registration For ASAP");
        mailMessage.setFrom("asap.revature@yahoo.com");
        mailMessage.setText("To confirm your account, please click here: "
        + "http://localhost:5000/");

    }



}
