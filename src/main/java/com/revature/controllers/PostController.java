package com.revature.controllers;


import com.revature.entities.Post;
import com.revature.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Post controller:
 *  endpoints for making, viewing, altering and removing posts/comments
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;


    /**
     * Constructor for autowiring the postService
     * @param postService service class for posts
     */
    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }


    /**
     * gets all posts from the database, along with related information such as poster and asset associated with it
     * @param request is the Http Servlet Request
     * @param response is the Http Servlet Response
     * @return all users in the database
     */
//    @Secured(allowedRoles = "ADMIN")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getAllPosts(){

        return postService.getAllPosts();
    }

    //    @Secured(allowedRoles = "ADMIN")
    @GetMapping(path="/{parentPostId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getPostsByParentId(@PathVariable Integer parentPostId){
        return postService.getPostsByParentPostId(parentPostId);
    }

    /**
     * Inserts a new post/comment into the database.
     * requires json body containing: id(==0), authorId, assetId, title, and textContent
     * @param newPost the json body of the clients request
     * @param request
     * @param response responds with status code
     */
//    @Secured(allowedRoles = {"ADMIN","BASIC"})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void makePost(@RequestBody Post newPost){

        postService.makePost(newPost);

    }

    /**
     * edits a post based on id.
     * requires json containing the id of the post to edit, the new title and new textContent
     * @param newPost the json body of the clients request
     * @param request
     * @param response responds with status code
     */
//    @Secured(allowedRoles = {"ADMIN","BASIC"})
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editPost(@RequestBody Post newPost){

        postService.editPost(newPost);

    }

    /**
     * Deletes a post/comment from the database.
     * requires admin and json body containing id of post to remove
     * @param post the json body of the clients request
     * @param request
     * @param response responds with status code
     */
//    @Secured(allowedRoles = "ADMIN")
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deletePost(@RequestBody Post post){
        
        postService.deletePost(post);
    }

}
