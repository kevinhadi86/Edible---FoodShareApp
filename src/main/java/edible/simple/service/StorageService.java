/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;

/**
 * @author Kevin Hadinata
 * @version $Id: StorageService.java, v 0.1 2019‐10‐03 17:35 Kevin Hadinata Exp $$
 */
public interface StorageService {

    String store(String file, String baseUrl);

    Path load(String filename);

    Resource loadAsResource(String filename);

}