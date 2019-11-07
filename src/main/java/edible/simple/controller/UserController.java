/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import edible.simple.model.User;
import edible.simple.payload.ApiResponse;
import edible.simple.payload.user.BaseUserResponse;
import edible.simple.payload.user.UpdatePasswordUser;
import edible.simple.payload.user.UpdateUserRequest;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.CategoryService;
import edible.simple.service.StorageService;
import edible.simple.service.UserService;

/**
 * @author Kevin Hadinata
 * @version $Id: HomeController.java, v 0.1 2019‐09‐13 12:10 Kevin Hadinata Exp $$
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService     userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StorageService  storageService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public BaseUserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {

        User user = userService.getUserByEmail(userPrincipal.getEmail());

        BaseUserResponse currentUser = new BaseUserResponse();
        BeanUtils.copyProperties(user, currentUser);

        return currentUser;
    }

    @GetMapping("/other/{username}")
    public BaseUserResponse getOtherUser(@PathVariable String username) {

        User user = userService.getUserByUsername(username);

        BaseUserResponse currentUser = new BaseUserResponse();
        BeanUtils.copyProperties(user, currentUser);

        return currentUser;
    }

    @PostMapping("/me/update/password")
    public ResponseEntity<ApiResponse> updateUserPassword(@CurrentUser UserPrincipal userPrincipal,
                                                          @RequestBody UpdatePasswordUser updatePasswordUser) {

        if (passwordEncoder.matches(updatePasswordUser.getPassword(),
            userPrincipal.getPassword())) {

            User user = userService.getUserByEmail(userPrincipal.getEmail());
            user.setPassword(passwordEncoder.encode(updatePasswordUser.getNewPassword()));

            userService.saveUser(user);

            return new ResponseEntity(new ApiResponse(true, "Update password success"),
                HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(false, "Password is wrong"),
            HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/me/update")
    public ResponseEntity<ApiResponse> updateUserProfile(@CurrentUser UserPrincipal userPrincipal,
                                                         @RequestBody UpdateUserRequest updateUserRequest) {

        User user = userService.getUserByEmail(userPrincipal.getEmail());

        if ((!user.getUsername().equals(updateUserRequest.getUsername())
             && userService.existsByUsername(updateUserRequest.getUsername()))
            || (!user.getEmail().equals(updateUserRequest.getEmail())
                && userService.existsByEmail(updateUserRequest.getEmail()))) {

            return new ResponseEntity(new ApiResponse(false, "Email or username already exists"),
                HttpStatus.BAD_REQUEST);
        }

        user.setEmail(updateUserRequest.getEmail());
        user.setUsername(updateUserRequest.getUsername());
        user.setName(updateUserRequest.getName());
        user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        user.setBio(updateUserRequest.getBio());
        user.setImageUrl(updateUserRequest.getImageUrl());

        user.setCity(updateUserRequest.getCity());

        if (userService.saveUser(user)) {

            return new ResponseEntity(new ApiResponse(true, "Update profile success"),
                HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "failed update user"),
            HttpStatus.BAD_REQUEST);
    }
}
