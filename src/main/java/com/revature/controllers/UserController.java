package com.revature.controllers;


import com.revature.annotations.Secured;
import com.revature.entities.Asset;
import com.revature.entities.User;
import com.revature.exceptions.InvalidRequestException;
import com.revature.services.AssetService;
import com.revature.services.EmailService;
import com.revature.services.UserService;
import com.revature.util.JwtGenerator;
import com.revature.dtos.Credentials;
import com.revature.dtos.Principal;
import com.revature.util.JwtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

    /**
     * Hosted Website URL
     */
    private final String WEB_URL = "http://p3-210119-java-enterprise.s3-website.us-east-2.amazonaws.com/";

    /**
     * Hosted API URL
     */
    private final String APP_URL = "http://ec2co-ecsel-1g0q6xc63i5af-1652680293.us-east-2.elb.amazonaws.com:5000/";
    private final UserService userService;
    private final AssetService assetService;
    private EmailService emailService;
    private final JwtGenerator jwtGenerator;
    private JwtParser jwtparser;

    /**
     * Constructor for auto wiring User Service and Email Service
     * @param userService service class for the users
     * @param emailService service class for email
     */
    @Autowired
    public UserController(UserService userService, AssetService assetService, EmailService emailService, JwtGenerator jwtGenerator, JwtParser jwtparser){
        this.userService = userService;
        this.assetService = assetService;
        this.emailService = emailService;
        this.jwtGenerator = jwtGenerator;
        this.jwtparser = jwtparser;
    }

    /**
     * Post method that will create a row in the database and also send an email
     * to the user's private email
     * @param newUser the json object of user data
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody User newUser) throws MessagingException {
        userService.registerUser(newUser);

        MimeMessage mailMessage = emailService.getJavaMailSender().createMimeMessage();
        mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(newUser.getEmail()));
        mailMessage.setSubject("Complete Registration For ASAP");
        mailMessage.setFrom("asap.revature@yahoo.com");

        String confirmationUrl = String.format("%s/users/confirmation/%s", APP_URL, newUser.getUsername());
        String html = String.format(
                "<div style=\"text-align:center;border:3px solid black;margin-left:25rem;margin-right:25rem;margin-top:25rem\">" +
                "<h3>Thank you for signing up for ASAP, %s!</h3>" +
                "<p>Click the following link to confirm your account: " +
                    "<a href=\"%s\">Confirm Your Account</a>" +
                    "</p" +
                "</div>", newUser.getFirstName(), confirmationUrl);

        mailMessage.setContent(html, "text/html; charset=utf-8");

        emailService.sendEmail(mailMessage);
    }

    /**
     * Get method that will update the confirmed account column
     * @param username the username of the user
     */
    @GetMapping(path = "/confirmation/{username}")
    public RedirectView confirmUserAccount(@PathVariable String username, HttpServletResponse response) {
        User user = userService.getUserByUsername(username);
        if (user == null) { throw new InvalidRequestException("No user with that username found."); }

        userService.confirmAccount(user.getUserId());
        return new RedirectView(WEB_URL);

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
    @Secured(allowedRoles = "ADMIN")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(HttpServletRequest request,HttpServletResponse response) {
        //String token = jwtparser.getTokenFromHeader(request);
        //Principal user = jwtparser.parseToken(token);
        response.setStatus(200);
        return userService.getallUsers();
    }

    /**
     * Retrieves the WatchList for the currently logged in User
     * @param request the HTTPRequest sent from the client
     * @return a List of Assets
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path="/watchlist")
    public List<Asset> getWatchlistForUser(HttpServletRequest request) {
        String token = jwtparser.getTokenFromHeader(request);
        Principal user = jwtparser.parseToken(token);

        List<Asset> list = userService.getWatchlistFromUser(user.getUsername());

        return list;
    }


    /**
     * Addes an Asset to the logged in User's Watchlist by Ticker
     * @param ticker the ticker for the company to add
     * @param request the HTTPRequest from the client
     */
    @PostMapping(path="/watchlist/{ticker}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAssetToWatchlist(@PathVariable String ticker, HttpServletRequest request) {
        String token = jwtparser.getTokenFromHeader(request);
        Principal user = jwtparser.parseToken(token);

        Asset asset = assetService.getAssetByTicker(ticker.toUpperCase());

        userService.addToWatchlist(user.getUsername(), asset);
    }

    /**
     * Deletes an Asset from the User's Watchlist by Ticker
     * @param ticker the ticker for the company to delete
     * @param request the HTTPRequest from the client
     */
    @DeleteMapping(path="/watchlist/{ticker}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAssetFromWatchlist(@PathVariable String ticker, HttpServletRequest request) {
        String token = jwtparser.getTokenFromHeader(request);
        Principal user = jwtparser.parseToken(token);

        Asset asset = assetService.getAssetByTicker(ticker.toUpperCase());

        userService.removeFromWatchlist(user.getUsername(), asset);
    }

    /**
     * Get method that will output information for a user. Need to have a valid JWT to be able
     * to hit this endpoint.
     * @return a {@code Principal} object of the user
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path="/profile")
    public Principal getUser(HttpServletRequest request){
        String token = jwtparser.getTokenFromHeader(request);
        Principal user = jwtparser.parseToken(token);
        User u = userService.getUserByUsername(user.getUsername());
        return new Principal(u);
    }

}
