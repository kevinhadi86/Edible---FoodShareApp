/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import java.util.HashSet;
import java.util.Set;

import edible.simple.model.Location;
import edible.simple.payload.user.*;
import edible.simple.service.LocationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import edible.simple.model.Category;
import edible.simple.model.User;
import edible.simple.model.dataEnum.CategoryName;
import edible.simple.payload.ApiResponse;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.CategoryService;
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
    LocationService locationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public BaseUserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUserByEmail(userPrincipal.getEmail());
        BaseUserResponse currentUser = new BaseUserResponse();
        BeanUtils.copyProperties(user, currentUser);
        return currentUser;
    }

    @GetMapping("/other/{username}")
    public BaseUserResponse getOtherUser(@PathVariable String username){
        User user = userService.getUserByUsername(username);
        BaseUserResponse currentUser = new BaseUserResponse();
        BeanUtils.copyProperties(user, currentUser);
        return currentUser;
    }

    @PostMapping("/me/update/password")
    public ResponseEntity<ApiResponse> updateUserPassword(@CurrentUser UserPrincipal userPrincipal, @RequestBody SaveUserRequest saveUserRequest){
        if (passwordEncoder.matches(saveUserRequest.getPassword(), userPrincipal.getPassword())) {
            User user = userService.getUserByEmail(userPrincipal.getEmail());
            user.setPassword(passwordEncoder.encode(saveUserRequest.getNewPassword()));
            userService.saveUser(user);
            return new ResponseEntity(new ApiResponse(true, "Update password success"),
                    HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Password is wrong"),
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/me/update")
    public ResponseEntity<ApiResponse> updateUserProfile(@CurrentUser UserPrincipal userPrincipal,
                                                         @RequestBody SaveUserRequest saveUserRequest) {
        if (passwordEncoder.matches(saveUserRequest.getPassword(), userPrincipal.getPassword())) {
            User user = userService.getUserByEmail(userPrincipal.getEmail());
            if (!user.getEmail().equals(saveUserRequest.getEmail())
                && userService.existsByEmail(saveUserRequest.getEmail())) {
                return new ResponseEntity(new ApiResponse(false, "Email already exists"),
                    HttpStatus.BAD_REQUEST);
            } else {
                user.setEmail(saveUserRequest.getEmail());
            }
            if (!user.getUsername().equals(saveUserRequest.getUsername())
                && userService.existsByUsername(saveUserRequest.getUsername())) {
                return new ResponseEntity(new ApiResponse(false, "Username already exists"),
                    HttpStatus.BAD_REQUEST);
            } else {
                user.setUsername(saveUserRequest.getUsername());
            }

            user.setName(saveUserRequest.getName());
            user.setPhonenumber(saveUserRequest.getPhonenumber());
            user.setImageurl(saveUserRequest.getImageurl());
            user.setBio(saveUserRequest.getBio());

            userService.saveUser(user);
            return new ResponseEntity(new ApiResponse(true, "Update profile success"),
                HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Password is wrong"),
            HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/me/preferences")
    public ResponseEntity<ApiResponse> setUserPreferences(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserPreferencesRequest userPreferencesRequest){

        Set<Category> preferences = new HashSet<>();

        for (String category : userPreferencesRequest.getCategories()){
            CategoryName categoryName = CategoryName.valueOf(category);
            if(categoryName == null){
                return new ResponseEntity(new ApiResponse(false,"Failed update user preferences, category not available: "+category), HttpStatus.BAD_REQUEST);
            }
            preferences.add(categoryService.getCategoryByName(categoryName));
        }
        SetCategoryRequest setCategoryRequest = new SetCategoryRequest(userPrincipal.getId(),preferences,"USER");
        if(categoryService.setCategory(setCategoryRequest)){
            return new ResponseEntity(new ApiResponse(true,"Success update user preferences"), HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false,"Failed update user preferences"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/me/location")
    public ResponseEntity<ApiResponse> setUserLocation(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserLocationRequest userLocationRequest){
        User user = userService.getUserById(userPrincipal.getId());
        Location location = locationService.getLocationByUser(user);
        if(location == null){
            location = new Location(user,userLocationRequest.getName());
        }else{
            location.setName(userLocationRequest.getName());
        }
        if(locationService.saveLocation(location)){
            return new ResponseEntity(new ApiResponse(true, "Success set user location"), HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Failed set user location"), HttpStatus.BAD_REQUEST);
    }

}
