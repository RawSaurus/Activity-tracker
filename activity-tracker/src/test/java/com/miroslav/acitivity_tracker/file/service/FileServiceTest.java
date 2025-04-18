package com.miroslav.acitivity_tracker.file.service;

import com.miroslav.acitivity_tracker.exception.StorageException;
import com.miroslav.acitivity_tracker.file.dto.FileResponse;
import com.miroslav.acitivity_tracker.file.mapper.FileMapper;
import com.miroslav.acitivity_tracker.file.mapper.FileMapperImpl;
import com.miroslav.acitivity_tracker.file.model.File;
import com.miroslav.acitivity_tracker.file.repository.FileRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileServiceTest {
    @Mock
    private FileRepository fileRepository;

    @Spy
    private FileMapper fileMapper = new FileMapperImpl();

    @Mock
    private UserContext userContext;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private FileService fileService;

    FileServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    private File file;
    private Profile profile;
    private User user;

    @BeforeEach
    public void setUp() {

        user = new User();
        user.setUserId(1);

        profile = new Profile();
        profile.setProfileId(1);
        profile.setUser(user);

        file = File.builder()
                .fileId(1)
                .fileCode("test-code")
                .name("test-file")
                .size(1024L)
                .postedBy(profile)
                .build();
    }

    @Test
    public void findById_shouldReturnFileResponse_whenFileExists() {
        when(fileRepository.findById(1)).thenReturn(Optional.of(file));
        when(fileMapper.toResponse(file)).thenReturn(new FileResponse("test-file", "test-code", 1024L));

        FileResponse response = fileService.findById(1);

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("test-file");
        verify(fileRepository, times(1)).findById(1);
    }

    @Test
    public void findById_shouldThrowException_whenFileDoesNotExist() {
        when(fileRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> fileService.findById(1));
    }

    @Test
    public void findByFileCode_shouldReturnFileResponse_whenFileExists() {
        when(fileRepository.findByFileCode("test-code")).thenReturn(Optional.of(file));
        when(fileMapper.toResponse(file)).thenReturn(new FileResponse("test-file", "test-code", 1024L));

        FileResponse response = fileService.findByFileCode("test-code");

        assertThat(response).isNotNull();
        assertThat(response.fileCode()).isEqualTo("test-code");
        verify(fileRepository, times(1)).findByFileCode("test-code");
    }

    @Test
    public void findByFileCode_shouldThrowException_whenFileDoesNotExist() {
        when(fileRepository.findByFileCode("test-code")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> fileService.findByFileCode("test-code"));
    }

    @Test
    public void uploadFile_shouldSaveFile() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("test-file");
        when(multipartFile.getSize()).thenReturn(1024L);
        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(fileRepository.save(any(File.class))).thenReturn(file);

        File savedFile = fileService.uploadFile(multipartFile);

        assertThat(savedFile).isNotNull();
        assertThat(savedFile.getName()).isEqualTo("test-file");
        verify(fileRepository, times(1)).save(any(File.class));
    }

    @Test
    public void deleteFile_shouldThrowStorageException_whenCanNotDeleteFile() throws IOException {

        assertThrows(StorageException.class, () -> fileService.deleteFile(file.getFilePath()));
    }

    @Test
    public void deleteFile_shouldThrowException_whenFileDoesNotExist() {
        when(fileRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> fileService.deleteFile(1));
    }
}
