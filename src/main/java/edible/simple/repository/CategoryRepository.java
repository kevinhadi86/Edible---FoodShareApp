/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edible.simple.model.Category;
import edible.simple.model.dataEnum.CategoryName;

/**
 * @author Kevin Hadinata
 * @version $Id: CategoryRepository.java, v 0.1 2019‐09‐17 19:01 Kevin Hadinata Exp $$
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    public Optional<Category> findByName(CategoryName name);
}