/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import edible.simple.model.User;
import edible.simple.payload.user.SaveNewUserRequest;

/**
 * @author Kevin Hadinata
 * @version $Id: UserService.java, v 0.1 2019‐09‐13 18:00 Kevin Hadinata Exp $$
 */
public interface UserService {

    public boolean saveNewUser(SaveNewUserRequest saveNewUserRequest);

    public void sendResetPasswordEmail(String email, String text);

    public User getUserByEmail(String email);

    public User getUserByUsername(String username);

    public User getUserById(Long id);

    public boolean saveUser(User user);

    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);
}