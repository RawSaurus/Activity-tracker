package com.miroslav.acitivity_tracker.file.repository;

import com.miroslav.acitivity_tracker.RepositoryTestConfig;
import com.miroslav.acitivity_tracker.file.model.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class FileRepositoryTest {
    @Autowired
    private FileRepository fileRepository;

    private File file;

    @BeforeEach
    public void setUp() {
        file = File.builder()
                .fileCode("test-code")
                .name("test-file")
                .build();

        fileRepository.save(file);
    }

    @AfterEach
    public void tearDown() {
        fileRepository.deleteAll();
    }

    @Test
    public void findByFileCode_shouldReturnFile_whenFileExists() {
        Optional<File> foundFile = fileRepository.findByFileCode("test-code");
        assertThat(foundFile).isPresent();
        assertThat(foundFile.get().getName()).isEqualTo("test-file");
    }

    @Test
    public void findByFileCode_shouldReturnEmpty_whenFileDoesNotExist() {
        Optional<File> foundFile = fileRepository.findByFileCode("non-existent-code");
        assertThat(foundFile).isEmpty();
    }

    @Test
    public void deleteByFileCode_shouldRemoveFile() {
        fileRepository.deleteByFileCode("test-code");
        Optional<File> deletedFile = fileRepository.findByFileCode("test-code");
        assertThat(deletedFile).isEmpty();
    }
}
