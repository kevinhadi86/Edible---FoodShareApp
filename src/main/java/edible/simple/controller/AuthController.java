/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import java.util.Base64;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import edible.simple.model.User;
import edible.simple.payload.ApiResponse;
import edible.simple.payload.auth.JwtAuthenticationResponse;
import edible.simple.payload.auth.LoginRequest;
import edible.simple.payload.auth.ResetPasswordRequest;
import edible.simple.payload.user.SaveNewUserRequest;
import edible.simple.repository.RoleRepository;
import edible.simple.security.JwtTokenProvider;
import edible.simple.service.AuthService;
import edible.simple.service.UserService;

/**
 * @author Kevin Hadinata
 * @version $Id: AuthController.java, v 0.1 2019‐09‐12 18:57 Kevin Hadinata Exp $$
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService           userService;

    @Autowired
    AuthService           authService;

    @Autowired
    RoleRepository        roleRepository;

    @Autowired
    PasswordEncoder       passwordEncoder;

    @Autowired
    JwtTokenProvider      jwtTokenProvider;

    @Autowired
    JavaMailSender        javaMailSender;

    @GetMapping("/HelloWorld")
    public ResponseEntity<ApiResponse> helloWorld(){
        return new ResponseEntity(new ApiResponse(true,"Hello World"),HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest);
        if (token != null) {
            return ResponseEntity.ok(new JwtAuthenticationResponse(true, token));
        }
        return new ResponseEntity(new ApiResponse(false, "Username or Email or Password is Wrong"),
            HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SaveNewUserRequest signUpRequest) {

        if(userService.saveNewUser(signUpRequest)){
            return new ResponseEntity(new ApiResponse(true, "Success Register User"), HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(false, "Failed Register User"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/sendConfirmationResetPasswordMail")
    public ResponseEntity<ApiResponse> sendConfirmationResetPasswordMail(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        User user = userService.getUserByEmail(resetPasswordRequest.getEmail());
        if (user != null) {
            Date now = new Date();
            String nowMiliseconds = Long.toString(now.getTime());
            String token = Base64.getEncoder()
                .encodeToString((user.getEmail() + "%%" + nowMiliseconds).getBytes());
            String url = "http://localhost:9002/api/auth/sendResetPasswordMail/" + token;
            String message = "Hello, this is from Edible. Please open this link and wait for another email if you want to confirm your password reset for your Edible account, here is the link: "
                             + url;
            userService.sendResetPasswordEmail(user.getEmail(), message);
            return new ResponseEntity(
                new ApiResponse(true, "Reset password email sent successfully"), HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Email not Exists"),
            HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/sendResetPasswordMail/{token}")
    public ResponseEntity<ApiResponse> sendResetPasswordMail(@PathVariable String token) {
        byte[] decodedToken = Base64.getDecoder().decode(token);
        String dataToken = new String(decodedToken);
        String[] data = dataToken.split("%%", 2);
        String email = data[0];
        User user = userService.getUserByEmail(email);
        if (user != null) {
            Date now = new Date();
            String newPassword = Long.toString(now.getTime());
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.saveUser(user);

            String message = "Hello, this is from Edible. This is your new password: "
                             + newPassword;
            userService.sendResetPasswordEmail(user.getEmail(), message);
            return new ResponseEntity(
                new ApiResponse(true, "Reset password email sent successfully"), HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Email not Exists"),
            HttpStatus.BAD_REQUEST);
    }
}
