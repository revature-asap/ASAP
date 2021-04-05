package com.revature.repositories;

import com.revature.entities.Post;
import com.revature.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * repository containing queries for accessing the posts table of the database
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    /**
     * find a post in the database with the specified id
     * @param id the id of the post
     * @return the post with the supplied id
     */
    Post findPostById(int id);


    /**
     * Queries the database for all posts containing a specified parent_post_id
     * @param id the parent_post_id to return
     * @return a list containing all posts with the specified requested parent_post_id
     */
    @Query(value = "SELECT * FROM posts WHERE parent_post_id = :id", nativeQuery = true)
    List<Post> getPostsByParentPostId(int id);
}
