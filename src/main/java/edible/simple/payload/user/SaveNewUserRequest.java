/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.user;

import javax.validation.constraints.NotBlank;

/**
 * @author Kevin Hadinata
 * @version $Id: SaveNewUserRequest.java, v 0.1 2019‐10‐02 17:31 Kevin Hadinata Exp $$
 */
public class SaveNewUserRequest extends SaveUserRequest {

    @NotBlank
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
