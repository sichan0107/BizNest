package com.tft.potato.util.file.service;

import com.tft.potato.util.file.entity.FileEntity;
import com.tft.potato.util.file.repo.FileJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileJpaRepository fileJpaRepository;
    private static final long LIMIT_SIZE = 20 * 1024 * 1024; // 20MB

    @Value("${file.upload.images-dir}")
    private String imageUploadDir;

    @PostConstruct
    public void init(){
        File uploadDir = new File(imageUploadDir);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
    }

    public Optional<FileEntity> searchFileByName(String fileName){
        Optional<FileEntity> file = fileJpaRepository.findByName(fileName);
        return file;
    }

    public Optional<FileEntity> searchFileById(String id){
        Optional<FileEntity> file = fileJpaRepository.findById(id);
        return file;
    }

    public Optional<List<FileEntity>> searchFilesUnderPath(String path){
        Optional<List<FileEntity>> fileList = fileJpaRepository.findAllByPath(path);
        return fileList;
    }

    public FileEntity saveFile(MultipartFile multipartFile) throws IOException, UncheckedIOException {

        if(multipartFile.getSize() > LIMIT_SIZE){
            throw new IllegalArgumentException("This file is too big. ");
        }

        FileEntity fileEntity = null;

        InputStream inputStream = multipartFile.getInputStream();
        Files.copy(inputStream, Paths.get(imageUploadDir), StandardCopyOption.REPLACE_EXISTING);

        inputStream.close();
        log.info("Image File Destination : " + imageUploadDir);

        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        long size = multipartFile.getSize();

        FileEntity file = new FileEntity(fileName,  1, imageUploadDir, contentType, size);
        fileEntity = fileJpaRepository.save(file);
//        try{
//            Files.copy(multipartFile.getInputStream(), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
//
//            log.info("File Destination : " + destPath);
//
//            String fileName = multipartFile.getOriginalFilename();
//            String contentType = multipartFile.getContentType();
//            long size = multipartFile.getSize();
//
//            FileEntity file = new FileEntity(fileName,  1,destPath, contentType, size);
//            fileEntity = fileJpaRepository.save(file);
//        }catch (IOException e){
//            log.error("File upload error.", e);
//        }

        return fileEntity;

    }



}
