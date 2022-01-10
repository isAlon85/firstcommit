package com.ialonso.firstcommit.repositories;

import com.ialonso.firstcommit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<User> findByEmail(String username);

    Boolean existsByEmail(String email);

}