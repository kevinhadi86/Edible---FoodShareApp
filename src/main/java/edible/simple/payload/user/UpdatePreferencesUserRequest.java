/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.user;

import java.util.List;

/**
 * @author Kevin Hadinata
 * @version $Id: UpdatePasswordUser.java, v 0.1 2019‐10‐02 17:24 Kevin Hadinata Exp $$
 */
public class UpdatePreferencesUserRequest {

    private List<String> preferences;

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }
}
