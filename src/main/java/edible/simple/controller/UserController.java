/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import edible.simple.model.Category;
import edible.simple.model.Review;
import edible.simple.model.dataEnum.CategoryName;
import edible.simple.payload.review.ReviewResponse;
import edible.simple.payload.transcation.TransactionResponse;
import edible.simple.payload.user.*;
import edible.simple.service.ReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import edible.simple.model.User;
import edible.simple.payload.ApiResponse;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.CategoryService;
import edible.simple.service.StorageService;
import edible.simple.service.UserService;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    ReviewService   reviewService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public BaseUserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {

        User user = userService.getUserByEmail(userPrincipal.getEmail());

        BaseUserResponse currentUser = new BaseUserResponse();
        BeanUtils.copyProperties(user, currentUser);

        if (!user.getCategories().isEmpty()) {

            List<String> preferences = new ArrayList<>();
            for (Category category : user.getCategories()) {
                preferences.add(category.getCategoryName().toString());
            }
            currentUser.setPreferences(preferences);
        }

        return currentUser;
    }

    @GetMapping("/other/{username}")
    public OtherUserResponse getOtherUser(@PathVariable String username) {

        User user = userService.getUserByUsername(username);

        OtherUserResponse currentUser = new OtherUserResponse();
        BeanUtils.copyProperties(user, currentUser);

        List<ReviewResponse> reviewResponses = new ArrayList<>();
        List<Review> userReviews = reviewService.getReviewByOwner(user);
        for (Review review : userReviews) {
            ReviewResponse reviewResponse = new ReviewResponse();
            BeanUtils.copyProperties(review, reviewResponse);

            BaseUserResponse baseUserResponse = new BaseUserResponse();
            BeanUtils.copyProperties(user,baseUserResponse);
            reviewResponse.setUser(baseUserResponse);

            TransactionResponse transactionResponse = new TransactionResponse();
            BeanUtils.copyProperties(review.getTransaction(),transactionResponse);
            reviewResponse.setTransaction(transactionResponse);

            Date createdDate = Date.from(review.getCreatedAt());
            String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(createdDate);
            reviewResponse.setDate(formattedDate);

            reviewResponses.add(reviewResponse);
        }
        currentUser.setReviewResponse(reviewResponses);

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

    @PostMapping("/me/update/preferences")
    public ResponseEntity<ApiResponse> updateUserPreferences(@CurrentUser UserPrincipal userPrincipal,
                                                             @RequestBody UpdatePreferencesUserRequest updateUserRequest) {

        User user = userService.getUserByEmail(userPrincipal.getEmail());

        Set<Category> categories = new HashSet<>();
        for (String category : updateUserRequest.getPreferences()) {
            CategoryName categoryName = CategoryName.getByCode(category);
            Category category1 = categoryService.getCategoryByName(categoryName);
            categories.add(category1);
        }
        user.setCategories(categories);

        if (userService.saveUser(user)) {

            return new ResponseEntity(new ApiResponse(true, "Update profile success"),
                HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "failed update user"),
            HttpStatus.BAD_REQUEST);
    }
}
