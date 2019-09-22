/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.user;

import java.util.List;

/**
 * @author Kevin Hadinata
 * @version $Id: UserPreferencesRequest.java, v 0.1 2019‐09‐18 12:01 Kevin Hadinata Exp $$
 */
public class UserPreferencesRequest {

    private List<String> categories;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
