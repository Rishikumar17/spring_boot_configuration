/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.springbootconfiguration.controller;

import com.google.common.net.HttpHeaders;
import com.personal.springbootconfiguration.exception.FileNotFoundException;
import com.personal.springbootconfiguration.payload.UploadFileResponse;
import com.personal.springbootconfiguration.config.FileStorageProperties;
import com.personal.springbootconfiguration.service.FileStorageService;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Rishi Kumar
 */
@RestController
public class FileContoller {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/UploadFile")
    public UploadFileResponse uploadfile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String filedownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName, filedownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/UploadMultiple")
    public List<UploadFileResponse> uploadMultiple(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadfile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileASResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        } catch (IOException e) {
            throw new FileNotFoundException(fileName + " not Found error.");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Spring Boot API Demo APP";

    }
}
