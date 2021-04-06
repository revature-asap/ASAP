package com.revature.services;

import com.revature.entities.Post;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    PostRepository postRepository;

    public PostService(PostRepository postRepository){this.postRepository=postRepository;}



    public List<Post> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        if(posts.isEmpty()){throw new ResourceNotFoundException();}
        return posts;
    }

    /**
     * Will retrieve all posts with the given parent post id.
     * If the {@code id} given is {@code -1}, will return all
     * posts with a parent post id of {@code null}.
     * @param id
     * @return a list of {@code Post} objects with the parent post id provided
     */
    public List<Post> getPostsByParentPostId(Integer id){
        List<Post> posts;
        if (id.equals(-1)) {
            posts = postRepository.getNullParentPosts();
        } else {
            posts = postRepository.getPostsByParentPostId(id);
        }
        if(posts.isEmpty()){throw new ResourceNotFoundException();}
        return posts;
    }

    public void makePost(Post newPost){
        newPost.setId(0);
        postRepository.save(newPost);
    }

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

    public void deletePost(Post newPost){
        postRepository.delete(newPost);
    }


}
