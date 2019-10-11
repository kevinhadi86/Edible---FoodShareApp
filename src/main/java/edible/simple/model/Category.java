/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

import edible.simple.model.dataEnum.CategoryName;

/**
 * @author Kevin Hadinata
 * @version $Id: Category.java, v 0.1 2019‐09‐17 18:36 Kevin Hadinata Exp $$
 */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long         id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "category_name")
    private CategoryName categoryName;

    public Category() {
    }

    public Category(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }
}
