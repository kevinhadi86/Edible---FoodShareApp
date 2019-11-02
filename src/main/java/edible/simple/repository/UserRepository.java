/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edible.simple.model.User;

/**
 * @author Kevin Hadinata
 * @version $Id: UserRepository.java, v 0.1 2019‐09‐11 15:13 Kevin Hadinata Exp $$
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> getAllByUsernameContaining(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


}