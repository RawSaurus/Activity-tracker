package com.miroslav.acitivity_tracker.file.service;

import com.miroslav.acitivity_tracker.exception.StorageException;
import com.miroslav.acitivity_tracker.file.dto.FileResponse;
import com.miroslav.acitivity_tracker.file.mapper.FileMapper;
import com.miroslav.acitivity_tracker.file.model.File;
import com.miroslav.acitivity_tracker.file.repository.FileRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FileService {

    private static final int FILE_CODE_LENGTH = 8;
    private final Path rootLocation = Paths.get("Files-Upload");

    private final UserContext userContext;
    private final ProfileRepository profileRepository;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileService(FileRepository fileRepository, FileMapper fileMapper, UserContext userContext, ProfileRepository profileRepository) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.userContext = userContext;
        this.profileRepository = profileRepository;
        createDir();
    }

    public FileResponse findById(Integer fileId){
        return fileMapper.toResponse(fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("File not found")));
    }

    public FileResponse findByFileCode(String fileCode){
        return fileMapper.toResponse(fileRepository.findByFileCode(fileCode)
                .orElseThrow(() -> new EntityNotFoundException("File not found")));
    }

    public Resource downloadFile(Integer fileId){
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("File not found"));
        System.out.println(file.getFileCode());
        try{
            Path filePath = rootLocation.resolve(file.getFileCode() + "-" + file.getName());
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
                throw new StorageException("Could not read file " + file.getName());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource downloadFile(String fileCode){
        File file = fileRepository.findByFileCode(fileCode)
                .orElseThrow(() -> new EntityNotFoundException("File not found"));
        System.out.println(file.getFileCode());
        try{
            Path filePath = rootLocation.resolve(fileCode + "-" + file.getName());
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
                throw new StorageException("Could not read file " + file.getName());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public File uploadFile(MultipartFile file){
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileCode = randomAlphaNumeric();

        File fileToSave = File.builder()
                .name(fileName)
                .fileCode(fileCode)
                .size(file.getSize())
                .postedBy(profile)
                .build();

        try(InputStream input = file.getInputStream()){
            Path filePath = rootLocation.resolve(fileCode + "-" + fileName);
            Files.copy(input, filePath, REPLACE_EXISTING);

        } catch(IOException exp){
            throw new StorageException("Failed to store file " + fileName, exp);
        }

        return fileRepository.save(fileToSave);
    }

    //TODO test
    public File updateFile(String fileCode, MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        File fileToSave = fileRepository.findByFileCode(fileCode)
                .orElseThrow(() -> new EntityNotFoundException("File not found"));

        try(InputStream input = file.getInputStream()){
            Path filePath = rootLocation.resolve(fileToSave.getFilePath());
            Files.copy(input, filePath, REPLACE_EXISTING);

        } catch(IOException exp){
            exp.printStackTrace();
        }

        fileToSave.setName(fileName);
        fileToSave.setSize(file.getSize());

        return fileRepository.save(fileToSave);
    }

    @Transactional
    public void deleteFile(Integer fileId){
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("File not found"));
        try {
            Path path = rootLocation.resolve(file.getFilePath());
            Files.delete(path);
        }catch(IOException e){
            throw new StorageException("Could not delete file " + file.getFilePath());
        }
        fileRepository.delete(file);
    }

    @Transactional
    public void deleteFile(String filePath){
        try {
            Path path = rootLocation.resolve(filePath);
            Files.delete(path);
        }catch(IOException e){
            throw new StorageException("Could not delete file " + filePath);
        }
        fileRepository.deleteByFileCode(filePath.split("-")[0]);
    }

    //works
    public void deleteDir(java.io.File file) {
        java.io.File[] contents = file.listFiles();
        if (contents != null) {
            for (java.io.File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

    //TODO test
    public void deleteAll(){
        List<File> list = fileRepository.findAll();
        for(File f : list){
            try{
                Path path = rootLocation.resolve(f.getFilePath());
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        fileRepository.deleteAll();
    }

    private void createDir(){
        try {
            if(!Files.exists(rootLocation)) {
                Files.createDirectory(rootLocation);
            }
        } catch (IOException e) {
            throw new StorageException("Could not create directory " + rootLocation);
        }
    }

    private String randomAlphaNumeric(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i<FILE_CODE_LENGTH;){
            int next = random.nextInt(48, 126);
            if((next >= 48 && next <= 57) || (next >= 65 && next <= 90) || (next >= 97 && next <= 122)) {
                sb.append((char)next);
                i++;
            }
        }
        return sb.toString();
    }
}
