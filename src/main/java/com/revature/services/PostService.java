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
import java.util.Optional;

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

    public List<PostDTO> getPostsByParentPostId(int id){
        List<Post> posts = postRepository.getPostsByParentPostId(id);
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
