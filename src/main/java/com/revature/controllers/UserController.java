package com.revature.controllers;


import com.revature.entities.User;
import com.revature.services.EmailService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to access user methods by hitting the end points
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private EmailService emailService;

    /**
     * Constructor for auto wiring User Service and Email Service
     * @param userService service class for the users
     * @param emailService service class for email
     */
    @Autowired
    public UserController(UserService userService,EmailService emailService){
        this.userService = userService;
        this.emailService = emailService;
    }

    //Post

    /**
     * Post method that will create a row in the database and also send an email
     * to the user's private email
     * @param newUser the json object of user data
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody User newUser) throws MessagingException {
        userService.registerUser(newUser);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newUser.getEmail());
        mailMessage.setSubject("Complete Registration For ASAP");
        mailMessage.setFrom("asap.revature@yahoo.com");

        // Need to change link when the API is hosted. . .
//        mailMessage.setText("To confirm your account, please click here: "
//        + "http://localhost:5000/users/confirmation/" + newUser.getUsername());

        String confirmationlink = "http://localhost:5000/users/confirmation/" + newUser.getUsername();
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>");
        htmlBuilder.append("<head><title>To confirm your account, please click below</title></head>");
        htmlBuilder.append("<body>");
//        htmlBuilder.append("<button onclick=\"window.location.href='https://w3docs.com';\">");
        htmlBuilder.append("<button onclick=\"window.location.href=\'");
        htmlBuilder.append(confirmationlink);
        htmlBuilder.append("';\">");

        htmlBuilder.append("Activate Account");
        htmlBuilder.append("</button>");
        htmlBuilder.append("<body>");
        htmlBuilder.append("</html>");

        MimeMessage mimeMessage = emailService.getJavaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("asap.revature@yahoo.com");
        helper.setSubject("Complete Registration For ASAP");
        helper.setText(htmlBuilder.toString());
        emailService.sendEmail(mimeMessage);
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



}
