/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import edible.simple.model.Category;
import edible.simple.model.dataEnum.CategoryName;

import java.util.List;

/**
 * @author Kevin Hadinata
 * @version $Id: CategoryService.java, v 0.1 2019‐09‐17 19:22 Kevin Hadinata Exp $$
 */
public interface CategoryService {

    public Category getCategoryByName(CategoryName name);

    public List<String> getAllCategories();

}