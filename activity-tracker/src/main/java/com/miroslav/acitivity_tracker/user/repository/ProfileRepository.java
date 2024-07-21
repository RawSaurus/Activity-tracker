package com.miroslav.acitivity_tracker.user.repository;

import com.miroslav.acitivity_tracker.user.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {


}
