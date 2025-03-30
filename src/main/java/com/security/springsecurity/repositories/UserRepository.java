package com.security.springsecurity.repositories;

import com.security.springsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.userName = :emailOrPassword OR u.email = :emailOrPassword")
    Optional<User> findByUserNameOrEmail(@Param("emailOrPassword") String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String username);
}
