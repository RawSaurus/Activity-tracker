package com.miroslav.acitivity_tracker.achievement.repository;

import com.miroslav.acitivity_tracker.achievement.model.TypeSuperclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

@NoRepositoryBean
public interface TypeSuperclassRepository<T extends TypeSuperclass, E extends Serializable> extends JpaRepository<T, E> {


}
