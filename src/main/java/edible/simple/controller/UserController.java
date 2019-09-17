/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import edible.simple.model.User;
import edible.simple.payload.auth.ChangeProfileRequest;
import edible.simple.payload.auth.CurrentUserResponse;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kevin Hadinata
 * @version $Id: HomeController.java, v 0.1 2019‐09‐13 12:10 Kevin Hadinata Exp $$
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    AuthService authService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public CurrentUserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal){
        User user = authService.getUserByEmail(userPrincipal.getEmail());
        CurrentUserResponse currentUser = new CurrentUserResponse();
        BeanUtils.copyProperties(user,currentUser);
        return currentUser;
    }

    @PostMapping("/me/update")
    public ResponseEntity<User> updateUserProfile(ChangeProfileRequest changeProfileRequest){
        return null;
    }
}
