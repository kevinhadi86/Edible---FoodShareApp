/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload;

import edible.simple.payload.offer.BaseOfferResponse;
import edible.simple.payload.user.BaseUserResponse;

import java.util.List;

/**
 * @author Kevin Hadinata
 * @version $Id: SearchResponse.java, v 0.1 2019‐11‐02 10:54 Kevin Hadinata Exp $$
 */
public class SearchResponse {

    List<BaseOfferResponse> baseOfferResponseList;

    List<BaseUserResponse> baseUserResponseList;

    public List<BaseOfferResponse> getBaseOfferResponseList() {
        return baseOfferResponseList;
    }

    public void setBaseOfferResponseList(List<BaseOfferResponse> baseOfferResponseList) {
        this.baseOfferResponseList = baseOfferResponseList;
    }

    public List<BaseUserResponse> getBaseUserResponseList() {
        return baseUserResponseList;
    }

    public void setBaseUserResponseList(List<BaseUserResponse> baseUserResponseList) {
        this.baseUserResponseList = baseUserResponseList;
    }
}
