/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import edible.simple.model.Review;
import edible.simple.model.Transaction;
import edible.simple.model.User;
import edible.simple.payload.ApiResponse;
import edible.simple.payload.offer.OfferResponse;
import edible.simple.payload.review.AddReviewRequest;
import edible.simple.payload.review.ReviewResponse;
import edible.simple.payload.transcation.TransactionResponse;
import edible.simple.payload.user.BaseUserResponse;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.ReviewService;
import edible.simple.service.TransactionService;
import edible.simple.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Hadinata
 * @version $Id: ReviewController.java, v 0.1 2019‐09‐23 00:40 Kevin Hadinata Exp $$
 */
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewService      reviewService;

    @Autowired
    UserService        userService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transaction/{id}")
    public ReviewResponse getReviewByTransaction(@CurrentUser UserPrincipal userPrincipal,
                                                 @PathVariable String id) {
        Transaction transaction = transactionService.getTransactionById(Long.parseLong(id));
        if (transaction != null
            && (transaction.getUser().getId() == userPrincipal.getId()
                || transaction.getOffer().getUser().getId() == userPrincipal.getId())) {
            Review review = reviewService.getReviewByTransaction(transaction);

            ReviewResponse reviewResponse = new ReviewResponse();
            reviewResponse.setId(review.getId());
            reviewResponse.setRating(review.getRating());
            reviewResponse.setReview(review.getReview());

            TransactionResponse transactionResponse = new TransactionResponse();
            BeanUtils.copyProperties(review.getTransaction(), transactionResponse);

            BaseUserResponse baseUserResponse = new BaseUserResponse();
            BeanUtils.copyProperties(review.getTransaction().getUser(),baseUserResponse);
            transactionResponse.setUser(baseUserResponse);

            OfferResponse offerResponse = new OfferResponse();
            BeanUtils.copyProperties(review.getTransaction().getOffer(),offerResponse);
            BaseUserResponse baseOfferUserResponse = new BaseUserResponse();
            BeanUtils.copyProperties(review.getTransaction().getOffer().getUser(),baseOfferUserResponse);
            offerResponse.setUser(baseOfferUserResponse);
            transactionResponse.setOffer(offerResponse);

            reviewResponse.setTransaction(transactionResponse);

            return reviewResponse;
        }

        return null;
    }

    @GetMapping("/myReview")
    public List<ReviewResponse> getMyReview(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUserById(userPrincipal.getId());

        List<Review> reviews = reviewService.getReviewByUser(user);
        List<ReviewResponse> myReview = new ArrayList<>();
        for (Review review : reviews) {
            ReviewResponse reviewResponse = new ReviewResponse();
            reviewResponse.setId(review.getId());
            reviewResponse.setRating(review.getRating());
            reviewResponse.setReview(review.getReview());

            TransactionResponse transactionResponse = new TransactionResponse();
            BeanUtils.copyProperties(review.getTransaction(), transactionResponse);

            BaseUserResponse baseUserResponse = new BaseUserResponse();
            BeanUtils.copyProperties(review.getTransaction().getUser(),baseUserResponse);
            transactionResponse.setUser(baseUserResponse);

            OfferResponse offerResponse = new OfferResponse();
            BeanUtils.copyProperties(review.getTransaction().getOffer(),offerResponse);
            BaseUserResponse baseOfferUserResponse = new BaseUserResponse();
            BeanUtils.copyProperties(review.getTransaction().getOffer().getUser(),baseOfferUserResponse);
            offerResponse.setUser(baseOfferUserResponse);
            transactionResponse.setOffer(offerResponse);

            reviewResponse.setTransaction(transactionResponse);

            myReview.add(reviewResponse);
        }
        return myReview;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addReview(@CurrentUser UserPrincipal userPrincipal,
                                                 @RequestBody AddReviewRequest addReviewRequest) {

        Transaction transaction = transactionService
            .getTransactionById(addReviewRequest.getTransactionId());
        if (transaction != null && transaction.getUser().getId() == userPrincipal.getId()) {
            Review review = new Review();
            review.setRating(addReviewRequest.getRating());
            review.setReview(addReviewRequest.getReview());
            review.setTransaction(transaction);

            if (reviewService.addReview(review) != null) {
                return new ResponseEntity<>(new ApiResponse(true, "Success add review"),
                    HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponse(false, "Failed add review"),
            HttpStatus.BAD_REQUEST);
    }
}
