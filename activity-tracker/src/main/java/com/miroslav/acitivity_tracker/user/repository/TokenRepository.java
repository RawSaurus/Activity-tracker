package com.miroslav.acitivity_tracker.user.repository;

import com.miroslav.acitivity_tracker.user.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

}
