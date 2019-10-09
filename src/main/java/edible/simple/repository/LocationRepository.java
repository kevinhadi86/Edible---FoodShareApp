/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edible.simple.model.Location;
import edible.simple.model.User;

/**
 * @author Kevin Hadinata
 * @version $Id: LocationRepository.java, v 0.1 2019‐09‐18 17:00 Kevin Hadinata Exp $$
 */
@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {

    public Location getByUser(User user);
}