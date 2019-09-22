/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.user;

import edible.simple.model.Category;
import edible.simple.model.dataEnum.CategoryName;

import java.util.List;
import java.util.Set;

/**
 * @author Kevin Hadinata
 * @version $Id: SetCategoryRequest.java, v 0.1 2019‐09‐17 19:11 Kevin Hadinata Exp $$
 */
public class SetCategoryRequest {

    private Long requestId;

    private Set<Category> categories;

    private String requestType;

    public SetCategoryRequest() {
    }

    public SetCategoryRequest(Long requestId, Set<Category> categories, String requestType) {
        this.requestId = requestId;
        this.categories = categories;
        this.requestType = requestType;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
