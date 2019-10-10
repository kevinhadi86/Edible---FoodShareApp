/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edible.simple.model.Unit;
import edible.simple.model.dataEnum.UnitName;

/**
 * @author Kevin Hadinata
 * @version $Id: UnitRepository.java, v 0.1 2019‐10‐03 11:32 Kevin Hadinata Exp $$
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    public Optional<Unit> findByUnitname(UnitName name);

}