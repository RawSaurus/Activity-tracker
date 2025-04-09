package com.miroslav.acitivity_tracker.template.repository;

import com.miroslav.acitivity_tracker.template.model.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Integer> {

    Optional<Template> findByName(String name);

    Page<Template> findAllByProfileProfileId(Integer profileId, Pageable pageable);
}
