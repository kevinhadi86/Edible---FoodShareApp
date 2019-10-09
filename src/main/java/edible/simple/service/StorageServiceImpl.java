/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import edible.simple.exception.StorageFileNotFoundException;
import edible.simple.storage.StorageProperties;

/**
 * @author Kevin Hadinata
 * @version $Id: StorageServiceImpl.java, v 0.1 2019‐10‐03 17:37 Kevin Hadinata Exp $$
 */
@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String store(String file, String baseUrl) {
        byte[] decodedImg = Base64.getDecoder()
                .decode(file.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();

        String filename = now.getTime() + ".jpg";
        Path destinationFile = Paths.get(String.valueOf(this.rootLocation), filename );
        try {
            Files.write(destinationFile, decodedImg);
        } catch (IOException e) {
           return null;
        }

        String imageUrl = baseUrl+filename;

        return imageUrl;
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }


}
