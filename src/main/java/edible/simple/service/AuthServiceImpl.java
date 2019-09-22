/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edible.simple.model.User;
import edible.simple.payload.auth.LoginRequest;
import edible.simple.repository.UserRepository;
import edible.simple.security.JwtTokenProvider;

/**
 * @author Kevin Hadinata
 * @version $Id: AuthServiceImpl.java, v 0.1 2019‐09‐17 18:59 Kevin Hadinata Exp $$
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository   userRepository;

    @Autowired
    PasswordEncoder  passwordEncoder;

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        String token = null;
        Optional<User> user = userRepository.findByUsernameOrEmail(
            loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail());
        if (user.isPresent()
            && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            token = jwtTokenProvider.generateToken(loginRequest.getUsernameOrEmail());
        }
        return token;
    }
}
