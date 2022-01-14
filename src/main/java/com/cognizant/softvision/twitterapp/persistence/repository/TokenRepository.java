package com.cognizant.softvision.twitterapp.persistence.repository;

import com.cognizant.softvision.twitterapp.persistence.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByMail(String mail);
    Optional<Token> findByToken(String token);
}
