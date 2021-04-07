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

@Service
public class PostService {

    PostRepository postRepository;
    UserService userService;


    @Autowired
    public PostService(PostRepository postRepository, UserService userService){
        this.postRepository=postRepository;
        this.userService = userService;
    }



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
