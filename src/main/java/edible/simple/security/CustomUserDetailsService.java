/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.security;

import edible.simple.model.User;
import edible.simple.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Kevin Hadinata
 * @version $Id: CustomUserDetailsService.java, v 0.1 2019‐09‐11 18:28 Kevin Hadinata Exp $$
 */

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with username or email: "+usernameOrEmail));
        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}
