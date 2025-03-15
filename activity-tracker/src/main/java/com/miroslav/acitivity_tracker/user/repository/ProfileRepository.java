package com.miroslav.acitivity_tracker.user.repository;

import com.miroslav.acitivity_tracker.user.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Optional<Profile> findByUserEmail(String email);

}
