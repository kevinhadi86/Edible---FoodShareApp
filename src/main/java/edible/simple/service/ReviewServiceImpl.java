/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edible.simple.model.Review;
import edible.simple.model.Transaction;
import edible.simple.model.User;
import edible.simple.repository.ReviewRepository;

/**
 * @author Kevin Hadinata
 * @version $Id: ReviewServiceImpl.java, v 0.1 2019‐09‐23 00:35 Kevin Hadinata Exp $$
 */
@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByUser(User user) {
        return reviewRepository.findAllByUser(user);
    }

    @Override
    public List<Review> getReviewByTransaction(Transaction transaction) {
        return reviewRepository.findByTransaction(transaction);
    }
}
