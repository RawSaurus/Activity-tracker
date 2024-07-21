package com.miroslav.acitivity_tracker.user.repository;

import com.miroslav.acitivity_tracker.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
