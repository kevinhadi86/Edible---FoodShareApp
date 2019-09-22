/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import edible.simple.payload.auth.LoginRequest;

/**
 * @author Kevin Hadinata
 * @version $Id: AuthService.java, v 0.1 2019‐09‐17 18:58 Kevin Hadinata Exp $$
 */
public interface AuthService {

    public String authenticateUser(LoginRequest loginRequest);

}