/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edible.simple.model.Review;
import edible.simple.model.Transaction;
import edible.simple.model.User;
import edible.simple.payload.ApiResponse;
import edible.simple.payload.offer.OtherUserOfferResponse;
import edible.simple.payload.review.AddReviewRequest;
import edible.simple.payload.review.ReviewResponse;
import edible.simple.payload.transcation.TransactionResponse;
import edible.simple.payload.user.BaseUserResponse;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.ReviewService;
import edible.simple.service.TransactionService;
import edible.simple.service.UserService;

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

    static Logger      logger = LoggerFactory.getLogger(ReviewController.class);

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
            BeanUtils.copyProperties(review.getTransaction().getUser(), baseUserResponse);
            transactionResponse.setUser(baseUserResponse);

            OtherUserOfferResponse otherUserOfferResponse = new OtherUserOfferResponse();
            BeanUtils.copyProperties(review.getTransaction().getOffer(), otherUserOfferResponse);
            BaseUserResponse baseOfferUserResponse = new BaseUserResponse();
            BeanUtils.copyProperties(review.getTransaction().getOffer().getUser(),
                baseOfferUserResponse);
            otherUserOfferResponse.setUser(baseOfferUserResponse);
            transactionResponse.setOffer(otherUserOfferResponse);

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

            Date createdDate = Date.from(review.getCreatedAt());
            String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(createdDate);
            reviewResponse.setDate(formattedDate);

            TransactionResponse transactionResponse = new TransactionResponse();
            BeanUtils.copyProperties(review.getTransaction(), transactionResponse);

            BaseUserResponse baseUserResponse = new BaseUserResponse();
            BeanUtils.copyProperties(review.getTransaction().getUser(), baseUserResponse);
            transactionResponse.setUser(baseUserResponse);

            OtherUserOfferResponse otherUserOfferResponse = new OtherUserOfferResponse();
            BeanUtils.copyProperties(review.getTransaction().getOffer(), otherUserOfferResponse);
            BaseUserResponse baseOfferUserResponse = new BaseUserResponse();
            BeanUtils.copyProperties(review.getTransaction().getOffer().getUser(),
                baseOfferUserResponse);
            otherUserOfferResponse.setUser(baseOfferUserResponse);
            transactionResponse.setOffer(otherUserOfferResponse);

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

        logger.info("transaction: "+transaction.toString());

        User user = userService.getUserById(userPrincipal.getId());

        Review currentReview = reviewService.getReviewByTransaction(transaction);
        if (currentReview.equals(null)) {
            logger.info("revienwya udah ada");
            return new ResponseEntity(new ApiResponse(false, "Review already done before"),
                HttpStatus.BAD_REQUEST);
        }

        if (transaction != null && transaction.getUser() == user) {
            logger.info("start generate review");
            Review review = new Review();
            review.setRating(addReviewRequest.getRating());
            review.setReview(addReviewRequest.getReview());
            review.setTransaction(transaction);

            if (reviewService.addReview(review) != null) {

                logger.info("udah berhasil save review");
                int rating = 0;
                int count = 0;

                List<Review> reviews = reviewService.getReviewByUser(user);

                for (Review allReview : reviews) {
                    rating += allReview.getRating();
                    count++;
                }

                user.setRating(rating / count);

                userService.saveUser(user);

                logger.info("udah berhasil save di usernya");
                return new ResponseEntity<>(new ApiResponse(true, "Success add review"),
                    HttpStatus.OK);
            }
        }
        logger.info("gagal checking");
        return new ResponseEntity<>(new ApiResponse(false, "Failed add review"),
            HttpStatus.BAD_REQUEST);
    }
}
