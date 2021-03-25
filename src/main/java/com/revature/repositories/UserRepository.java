package com.revature.repositories;

import com.revature.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    Optional<User> findUserByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET account_confirmed = true WHERE user_id = :id", nativeQuery = true)
    void confirmedAccount(int id);

}
