package com.miroslav.acitivity_tracker.role.repository;

import com.miroslav.acitivity_tracker.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String role);
}
