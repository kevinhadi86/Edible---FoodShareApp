/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.user;

import edible.simple.payload.review.ReviewResponse;

import java.util.List;

/**
 * @author Kevin Hadinata
 * @version $Id: OtherUserResponse.java, v 0.1 2019‐11‐09 14:19 Kevin Hadinata Exp $$
 */
public class OtherUserResponse extends BaseUserResponse {

    private List<ReviewResponse> reviewResponse;

    public List<ReviewResponse> getReviewResponse() {
        return reviewResponse;
    }

    public void setReviewResponse(List<ReviewResponse> reviewResponse) {
        this.reviewResponse = reviewResponse;
    }
}
