/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.springbootconfiguration.service;

import com.personal.springbootconfiguration.exception.FileNotFoundException;
import com.personal.springbootconfiguration.exception.FileStorageException;
import com.personal.springbootconfiguration.config.FileStorageProperties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rishi Kumar
 */
@Service
public class FileStorageService {

    private Path fileStoragPath;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStoragPath = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStoragPath);
        } catch (IOException ex) {
            System.out.println("Could not create the directory where the upload");
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains((".."))) {
                throw new FileStorageException("Sorry Filename contains invalid name");
            }

            Path targetPath = this.fileStoragPath.resolve(fileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (Exception e) {
            throw new FileStorageException("Could now store File" + fileName + ". Please try agin", e);
        }
    }

    public Resource loadFileASResource(String fileName) {

        try {
            Path filePath = this.fileStoragPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("File not found " + fileName, e);
        }
    }

}
