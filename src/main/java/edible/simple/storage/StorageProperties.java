/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kevin Hadinata
 * @version $Id: StorageProperties.java, v 0.1 2019‐09‐27 17:20 Kevin Hadinata Exp $$
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
