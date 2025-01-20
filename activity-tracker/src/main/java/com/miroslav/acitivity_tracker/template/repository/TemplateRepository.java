package com.miroslav.acitivity_tracker.template.repository;

import com.miroslav.acitivity_tracker.template.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Integer> {

    Optional<Template> findByName(String name);
}
