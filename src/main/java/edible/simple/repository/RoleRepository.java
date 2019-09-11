/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import edible.simple.model.Role;
import edible.simple.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Kevin Hadinata
 * @version $Id: RoleRepository.java, v 0.1 2019‐09‐11 15:15 Kevin Hadinata Exp $$
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName roleName);
}