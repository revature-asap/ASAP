package com.revature.services;

import com.revature.entities.Post;
import com.revature.entities.User;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    PostRepository postRepository;

    public PostService(PostRepository postRepository){this.postRepository=postRepository;}



    public List<Post> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        if(posts.isEmpty()){throw new ResourceNotFoundException();}
        return posts;
    }

    public void makePost(Post newPost){
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
