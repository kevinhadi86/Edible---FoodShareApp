/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edible.simple.model.Category;
import edible.simple.model.dataEnum.CategoryName;
import edible.simple.repository.CategoryRepository;

/**
 * @author Kevin Hadinata
 * @version $Id: CategoryServiceImpl.java, v 0.1 2019‐09‐18 10:54 Kevin Hadinata Exp $$
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserService userService;

    @Override
    public Category getCategoryByName(CategoryName name) {
        Optional<Category> category =  categoryRepository.findByCategoryName(name);
        if(category.isPresent()){
            return category.get();
        }
        return null;
    }

    @Override
    public List<String> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        List<String> categoryName = new ArrayList<>();
        for (Category category : categories){
            categoryName.add(category.getCategoryName().toString());
        }

        return categoryName;
    }
}
