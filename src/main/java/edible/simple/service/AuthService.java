/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import org.springframework.http.ResponseEntity;

import edible.simple.model.User;
import edible.simple.payload.ApiResponse;
import edible.simple.payload.auth.LoginRequest;
import edible.simple.payload.auth.SignUpRequest;

/**
 * @author Kevin Hadinata
 * @version $Id: AuthService.java, v 0.1 2019‐09‐13 18:00 Kevin Hadinata Exp $$
 */
public interface AuthService  {

    public String authenticateUser(LoginRequest loginRequest);

    public ResponseEntity<ApiResponse> registerUser(SignUpRequest signUpRequest);

    public void sendResetPasswordEmail(String email, String newPassword);

    public User getUserByEmail(String email);

    public User saveUser(User user);
}