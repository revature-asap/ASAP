package com.revature.controllers;


import com.revature.dtos.Principal;
import com.revature.entities.Post;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.services.PostService;
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
@RequestMapping("/posts")
public class PostController {

    private final String WEB_URL = "http://p3-210119-java-enterprise.s3-website.us-east-2.amazonaws.com/";
    private final String APP_URL = "http://localhost:5000";
    private final PostService postService;
    private final JwtGenerator jwtGenerator;
    private JwtParser jwtparser;


    @Autowired
    public PostController(PostService postService, JwtGenerator jwtGenerator, JwtParser jwtparser){
        this.postService = postService;
        this.jwtGenerator = jwtGenerator;
        this.jwtparser = jwtparser;
    }



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getAllPosts(HttpServletRequest request, HttpServletResponse response){

        response.setStatus(200);
        return postService.getAllPosts();

    }




}
