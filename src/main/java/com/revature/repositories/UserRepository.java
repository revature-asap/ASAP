package com.revature.repositories;

import com.revature.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * This class is responsible for communicating to the database and manipulating the users table
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    /**
     * Finds the User by inputting username
     * @param username the username of the user
     * @return an optional of the user
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Updates the confirmed account column to true where user id equals to the input id
     * @param id user's id
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET account_confirmed = true WHERE user_id = :id", nativeQuery = true)
    void confirmedAccount(int id);

}
