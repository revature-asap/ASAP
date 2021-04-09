package com.revature.services;

import com.revature.dtos.PostDTO;
import com.revature.entities.Post;
import com.revature.entities.User;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class that servers a an intermidiary between the enpoints and the repository
 */
@Service
public class PostService {

    PostRepository postRepository;
    UserService userService;

    /**
     * Constructor for post service class that autowires the post repo
     * @param postRepository
     * @param userService
     */
    @Autowired
    public PostService(PostRepository postRepository, UserService userService){
        this.postRepository=postRepository;
        this.userService = userService;
    }


    /**
     * Service method to retrieve all posts residing in the database
     * @return all posts from database
     */
    public List<Post> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        if(posts.isEmpty()){throw new ResourceNotFoundException();}
        return posts;
    }

    /**
     * Service method to retrieve all posts with a specific parentPostId
     * If the {@code id} given is {@code -1}, will return all
     * posts with a parent post id of {@code null}.
     * @param id {@code id} of the parent post
     * @return a list of {@code Post} objects with the parent post id provided
     */
    public List<PostDTO> getPostsByParentPostId(Integer id){
        List<Post> posts = postRepository.getPostsByParentPostId(id);
        if (id.equals(-1)) {
            posts = postRepository.getNullParentPosts();
        } else {
            posts = postRepository.getPostsByParentPostId(id);
        }
        if(posts.isEmpty()){throw new ResourceNotFoundException();}

        List<PostDTO> postsDto = new ArrayList<>();
        for (Post cur: posts) {
            User user = userService.getUser(cur.getAuthorId());
            PostDTO dto = new PostDTO(cur.getId(), cur.getAuthorId(), cur.getParentPostId(),
            cur.getTitle(), cur.getTextContent(), cur.getCreationTimestamp(),  user.getUsername());
            postsDto.add(dto);
        }
        return postsDto;
    }

    /**
     * Sevice method to save a new post into the database
     * @param newPost the post to be saved into the database
     */
    public void makePost(Post newPost){
        newPost.setId(0);
        postRepository.save(newPost);
    }

    /**
     * Service method to update the title and textContent of a post that already exists in the database
     * @param updatedPost post containing the id of the post to alter, and the updated title and textContent
     */
    public void editPost(Post updatedPost){
        Post postToEdit= postRepository.findPostById(updatedPost.getId());
        assert postToEdit != null;
        postToEdit.setTitle(updatedPost.getTitle());
        postToEdit.setTextContent(updatedPost.getTextContent());
        postRepository.save(postToEdit);
    }

    /**
     * Service method for removing a post
     * @param newPost the post to be deleted
     */
    public void deletePost(Post newPost){
        postRepository.delete(newPost);
    }


}
