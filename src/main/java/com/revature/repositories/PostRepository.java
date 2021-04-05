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

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findPostById(int id);


    @Query(value = "SELECT * FROM posts WHERE parent_post_id = :id", nativeQuery = true)
    List<Post> getPostsByParentPostId(int id);
}
