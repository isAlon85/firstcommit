package com.ialonso.firstcommit.repositories;

import com.ialonso.firstcommit.entities.PasswordResetToken;
import com.ialonso.firstcommit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByUser(User user);

    Boolean existsByUser(User user);

    PasswordResetToken findByToken(String token);

    Boolean existsByToken(String token);

}
