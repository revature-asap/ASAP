package com.revature.repositories;

import com.revature.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findPostById(int id);

    @Query(value = "SELECT * FROM posts WHERE parent_post_id = :id", nativeQuery = true)
    List<Post> getPostsByParentPostId(Integer id);

    @Query(value = "SELECT * FROM posts WHERE parent_post_id is null", nativeQuery = true)
    List<Post> getNullParentPosts();
}
