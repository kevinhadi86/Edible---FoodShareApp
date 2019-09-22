/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.offer;

import edible.simple.model.User;
import edible.simple.payload.user.BaseUserResponse;

/**
 * @author Kevin Hadinata
 * @version $Id: OfferResponse.java, v 0.1 2019‐09‐19 18:42 Kevin Hadinata Exp $$
 */
public class OfferResponse extends MyOfferResponse {

    private BaseUserResponse user;
    private String location;

    public BaseUserResponse getUser() {
        return user;
    }

    public void setUser(BaseUserResponse user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
