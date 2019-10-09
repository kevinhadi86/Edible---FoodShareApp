/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.user;

import javax.validation.constraints.NotBlank;

/**
 * @author Kevin Hadinata
 * @version $Id: UpdatePasswordUser.java, v 0.1 2019‐10‐02 17:24 Kevin Hadinata Exp $$
 */
public class UpdatePasswordUser {

    @NotBlank
    private String password;
    @NotBlank
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
