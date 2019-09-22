/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.review;

/**
 * @author Kevin Hadinata
 * @version $Id: AddReviewRequest.java, v 0.1 2019‐09‐23 00:52 Kevin Hadinata Exp $$
 */
public class AddReviewRequest {

    private Float rating;
    private String review;
    private Long transactionId;

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
