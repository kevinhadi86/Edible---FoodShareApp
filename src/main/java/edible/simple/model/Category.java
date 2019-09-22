/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @Column(length = 60)
    private CategoryName name;

    public Category() {
    }

    public Category(CategoryName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryName getName() {
        return name;
    }

    public void setName(CategoryName name) {
        this.name = name;
    }
}
