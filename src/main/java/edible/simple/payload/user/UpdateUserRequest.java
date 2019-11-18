/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.user;

import javax.validation.constraints.NotBlank;

/**
 * @author Kevin Hadinata
 * @version $Id: UpdateUserRequest.java, v 0.1 2019‐09‐16 13:31 Kevin Hadinata Exp $$
 */
public class UpdateUserRequest extends SaveUserRequest {

    @NotBlank
    private Long id;

    private String imageUrl;

    private String bio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
