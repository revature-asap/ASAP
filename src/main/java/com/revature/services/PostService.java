package com.revature.services;

import com.revature.entities.Post;
import com.revature.entities.User;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that servers a an intermidiary between the enpoints and the repository
 */
@Service
public class PostService {

    PostRepository postRepository;

    /**
     * Constructor for post service class that autowires the post repo
     * @param postRepository
     */
    public PostService(PostRepository postRepository){this.postRepository=postRepository;}


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
     * @param id id of the parent post
     * @return all post with the specified parent post id
     */
    public List<Post> getPostsByParentPostId(int id){
        List<Post> posts = postRepository.getPostsByParentPostId(id);
        if(posts.isEmpty()){throw new ResourceNotFoundException();}
        return posts;
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
        if(postToEdit==null){
            //TODO ERROR
        }
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
