/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Kevin Hadinata
 * @version $Id: SaveUserRequest.java, v 0.1 2019‐10‐02 17:17 Kevin Hadinata Exp $$
 */
public class SaveUserRequest {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String city;

    public SaveUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
