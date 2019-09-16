/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.auth;

import javax.validation.constraints.NotBlank;

/**
 * @author Kevin Hadinata
 * @version $Id: ResetPasswordRequest.java, v 0.1 2019‐09‐16 16:10 Kevin Hadinata Exp $$
 */
public class ResetPasswordRequest {

    @NotBlank
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
