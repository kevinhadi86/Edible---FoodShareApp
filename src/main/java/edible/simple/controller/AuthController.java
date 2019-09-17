/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import java.util.Date;

import javax.validation.Valid;

import edible.simple.payload.ApiResponse;
import edible.simple.payload.auth.JwtAuthenticationResponse;
import edible.simple.payload.auth.LoginRequest;
import edible.simple.payload.auth.ResetPasswordRequest;
import edible.simple.payload.auth.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import edible.simple.model.User;
import edible.simple.repository.RoleRepository;
import edible.simple.security.JwtTokenProvider;
import edible.simple.service.AuthService;

/**
 * @author Kevin Hadinata
 * @version $Id: AuthController.java, v 0.1 2019‐09‐12 18:57 Kevin Hadinata Exp $$
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager    authenticationManager;

    @Autowired
    AuthService              authService;

    @Autowired
    RoleRepository           roleRepository;

    @Autowired
    PasswordEncoder          passwordEncoder;

    @Autowired
    JwtTokenProvider         jwtTokenProvider;

    @Autowired
    JavaMailSender           javaMailSender;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest);
        if(token != null){
            return ResponseEntity.ok(new JwtAuthenticationResponse(true, token));
        }
        return new ResponseEntity(new ApiResponse(false,"Username or Email or Password is Wrong"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @PostMapping("/sendResetPasswordMail")
    public ResponseEntity<ApiResponse> sendResetPasswordMail(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        User user = authService.getUserByEmail(resetPasswordRequest.getEmail());
        if (user != null) {
            Date now = new Date();
            String newPassword = Long.toString(now.getTime());
            user.setPassword(passwordEncoder.encode(newPassword));
            authService.saveUser(user);
            authService.sendResetPasswordEmail(user.getEmail(), newPassword);
            return new ResponseEntity(new ApiResponse(true,"Reset password email sent successfully"),HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Email not Exists"),
            HttpStatus.BAD_REQUEST);
    }
}
