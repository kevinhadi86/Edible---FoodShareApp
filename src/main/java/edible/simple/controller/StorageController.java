/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import edible.simple.exception.StorageFileNotFoundException;
import edible.simple.service.StorageService;

/**
 * @author Kevin Hadinata
 * @version $Id: UploadController.java, v 0.1 2019‐09‐27 16:57 Kevin Hadinata Exp $$
 */
@RestController
@RequestMapping("/api/upload")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public List<String> handleFileUpload(@RequestParam("files") List<MultipartFile> files) {

        List<String> response = new ArrayList<>();
        for (MultipartFile file: files){
            storageService.store(file);
            response.add(file.getOriginalFilename());
        }

        return response;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
