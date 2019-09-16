/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edible.simple.exception.AppException;
import edible.simple.model.Role;
import edible.simple.model.RoleName;
import edible.simple.model.User;
import edible.simple.payload.ApiResponse;
import edible.simple.payload.auth.LoginRequest;
import edible.simple.payload.auth.SignUpRequest;
import edible.simple.repository.RoleRepository;
import edible.simple.repository.UserRepository;
import edible.simple.security.JwtTokenProvider;

/**
 * @author Kevin Hadinata
 * @version $Id: AuthServiceImpl.java, v 0.1 2019‐09‐15 20:57 Kevin Hadinata Exp $$
 */
@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        return jwtTokenProvider.generateToken(loginRequest.getUsernameOrEmail());
    }

    @Override
    public ResponseEntity<ApiResponse> registerUser(SignUpRequest signUpRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getUsername(), signUpRequest.getPhonenumber());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/{username}").buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

    @Override
    public void sendResetPasswordEmail(String email, String newPassword) {

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            User userValue = user.get();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userValue.getEmail());
            message.setSubject("Reset Password Edible");
            message.setText("New Password: "+newPassword);
            javaMailSender.send(message);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByUsernameOrEmail(email,email);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
