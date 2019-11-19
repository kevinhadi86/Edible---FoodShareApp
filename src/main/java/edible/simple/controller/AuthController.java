/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edible.simple.service.CategoryService;
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
import edible.simple.service.LocationService;
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

    @Autowired
    LocationService       locationService;

    @Autowired
    CategoryService       categoryService;

    @GetMapping("/HelloWorld")
    public ResponseEntity<ApiResponse> helloWorld() {
        return new ResponseEntity(new ApiResponse(true, "Hello World"), HttpStatus.OK);
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

        if (userService.saveNewUser(signUpRequest)) {
            return new ResponseEntity(new ApiResponse(true, "Success Register User"),
                HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(false, "Failed Register User"),
            HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/sendConfirmationResetPasswordMail")
    public ResponseEntity<ApiResponse> sendConfirmationResetPasswordMail(@RequestBody ResetPasswordRequest resetPasswordRequest,
                                                                         HttpServletRequest request) {
        User user = userService.getUserByEmail(resetPasswordRequest.getEmail());

        if (user == null) {
            return new ResponseEntity(new ApiResponse(false, "Email not Exists"),
                HttpStatus.BAD_REQUEST);
        }

        String token = createToken(user.getEmail());

        String baseUrl = String.format("%s://%s:%d/api/auth/sendResetPasswordMail/",
            request.getScheme(), request.getServerName(), request.getServerPort());
        String url = baseUrl + token;
        String message = "Hello, this is from Edible. Please open this link and wait for another email if you want to confirm your password reset for your Edible account, here is the link: "
                         + url;
        userService.sendResetPasswordEmail(user.getEmail(), message);
        return new ResponseEntity(new ApiResponse(true, "Reset password email sent successfully"),
            HttpStatus.OK);

    }

    @GetMapping("/sendResetPasswordMail/{token}")
    public ResponseEntity<ApiResponse> sendResetPasswordMail(@PathVariable String token) {

        if (token == null) {
            return new ResponseEntity(new ApiResponse(false, "Request Invalid"),
                HttpStatus.BAD_REQUEST);
        }

        String email = getEmailFromToken(token);

        User user = userService.getUserByEmail(email);

        if (user == null) {
            return new ResponseEntity(new ApiResponse(false, "User not Exists"),
                HttpStatus.BAD_REQUEST);
        }

        String newPassword = generateNewPassword();

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);

        String message = "Hello, this is from Edible. This is your new password: " + newPassword;
        userService.sendResetPasswordEmail(user.getEmail(), message);

        return new ResponseEntity(new ApiResponse(true,
            "Please check your email again, there should be a new about containing your password"),
            HttpStatus.OK);

    }

    @GetMapping("/getProvince")
    public Set<String> getAllProvince() {
        return locationService.getAllProvince();
    }

    @GetMapping("/getCity/{province}")
    public Set<String> getCityByProvince(@PathVariable String province) {
        return locationService.getCityByProvince(province);
    }

    private String createToken(String email) {

        Date now = new Date();
        String nowMiliseconds = Long.toString(now.getTime());

        return Base64.getEncoder().encodeToString((email + "%%" + nowMiliseconds).getBytes());
    }

    @GetMapping("/getCategory")
    public List<String> getAllCategories() {
        return categoryService.getAllCategories();
    }

    private String getEmailFromToken(String token) {

        byte[] decodedToken = Base64.getDecoder().decode(token);

        String dataToken = new String(decodedToken);
        String[] data = dataToken.split("%%", 2);

        return data[0];
    }

    private String generateNewPassword() {

        Date now = new Date();
        return Long.toString(now.getTime());
    }
}
