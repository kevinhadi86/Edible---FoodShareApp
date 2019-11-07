/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edible.simple.exception.AppException;
import edible.simple.model.Role;
import edible.simple.model.User;
import edible.simple.model.dataEnum.RoleName;
import edible.simple.payload.user.SaveNewUserRequest;
import edible.simple.repository.LocationRepository;
import edible.simple.repository.RoleRepository;
import edible.simple.repository.UserRepository;
import edible.simple.security.JwtTokenProvider;

/**
 * @author Kevin Hadinata
 * @version $Id: AuthServiceImpl.java, v 0.1 2019‐09‐15 20:57 Kevin Hadinata Exp $$
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JwtTokenProvider   jwtTokenProvider;

    @Autowired
    UserRepository     userRepository;

    @Autowired
    PasswordEncoder    passwordEncoder;

    @Autowired
    RoleRepository     roleRepository;

    @Autowired
    JavaMailSender     javaMailSender;

    @Autowired
    LocationRepository locationRepository;

    @Override
    public boolean saveNewUser(SaveNewUserRequest saveNewUserRequest) {
        if (Boolean.TRUE
            .equals(userRepository.existsByUsername(saveNewUserRequest.getUsername()))) {
            return false;
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(saveNewUserRequest.getEmail()))) {
            return false;
        }
        User user = new User();

        BeanUtils.copyProperties(saveNewUserRequest, user);

        user.setCity(saveNewUserRequest.getCity());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
            .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        if (userRepository.save(user).getId() != null) {
            return true;
        }

        return false;

    }

    @Override
    public void sendResetPasswordEmail(String email, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Password Edible");
        message.setText(text);
        message.setFrom("no-reply@edible.com");
        javaMailSender.send(message);

    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByUsernameOrEmail(email, email);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsernameOrEmail(username, username);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public List<User> getAllUserByUsername(String username) {
        return userRepository.getAllByUsernameContaining(username);
    }

    @Override
    public boolean saveUser(User user) {
        if (!userRepository.save(user).equals(null)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
