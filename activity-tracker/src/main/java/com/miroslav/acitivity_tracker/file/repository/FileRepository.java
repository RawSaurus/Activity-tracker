package com.miroslav.acitivity_tracker.file.repository;

import com.miroslav.acitivity_tracker.file.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Integer> {

    @Query("""
            SELECT file
            FROM File file
            WHERE file.fileCode = :fileCode
            """)
    Optional<File> findByFileCode(String fileCode);

    void deleteByFileCode(String fileCode);
}
