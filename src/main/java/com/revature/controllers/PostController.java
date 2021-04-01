package com.revature.controllers;


import com.revature.dtos.Principal;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.services.UserService;
import com.revature.util.JwtGenerator;
import com.revature.util.JwtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final String WEB_URL = "http://p3-210119-java-enterprise.s3-website.us-east-2.amazonaws.com/";
    private final String APP_URL = "http://localhost:5000";
    private final UserService userService;
    private final JwtGenerator jwtGenerator;
    private JwtParser jwtparser;


    @Autowired
    public PostController(UserService userService, JwtGenerator jwtGenerator, JwtParser jwtparser){
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
        this.jwtparser = jwtparser;
    }



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllPosts(HttpServletRequest request, HttpServletResponse response){
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
