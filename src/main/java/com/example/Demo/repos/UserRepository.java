package com.example.Demo.repos;


import com.example.Demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id); // find the user by id

    Optional<User> findByEmail(String email);//method to find the user by email

    Optional<User> findByUsername(String username);

}

