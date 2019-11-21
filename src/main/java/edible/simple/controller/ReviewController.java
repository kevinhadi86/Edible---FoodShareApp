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

import edible.simple.model.dataEnum.StatusEnum;
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
    public List<ReviewResponse> getReviewByTransaction(@CurrentUser UserPrincipal userPrincipal,
                                                 @PathVariable String id) {
        List<ReviewResponse> reviewResponseList = new ArrayList<>();

        Transaction transaction = transactionService.getTransactionById(Long.parseLong(id));
        if (transaction != null
            && (transaction.getUser().getId() == userPrincipal.getId()
                || transaction.getOffer().getUser().getId() == userPrincipal.getId())) {
            List<Review> reviewList = reviewService.getReviewByTransaction(transaction);

            for (Review review : reviewList){
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

                BaseUserResponse fillerUserResponse = new BaseUserResponse();
                BeanUtils.copyProperties(review.getTransaction().getUser(), fillerUserResponse);
                reviewResponse.setUser(fillerUserResponse);

                reviewResponseList.add(reviewResponse);
            }

            return reviewResponseList;
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

        if (transaction == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Transaction not exists"),
                HttpStatus.BAD_REQUEST);
        } else if (!(transaction.getStatus().equals(StatusEnum.DONE)
                     || transaction.getStatus().equals(StatusEnum.REVIEWED))) {
            return new ResponseEntity<>(new ApiResponse(false, "Transaction not Done"),
                HttpStatus.BAD_REQUEST);
        }

        logger.info("transaction: " + transaction.toString());

        User user = userService.getUserById(userPrincipal.getId());

        User owner = null;
        if (user.getId() == transaction.getOffer().getUser().getId()) {
            owner = transaction.getUser();
            logger.info("Owner: " + owner.getUsername());
        } else if (user.getId() == transaction.getUser().getId()) {
            owner = transaction.getOffer().getUser();
            logger.info("Owner: " + owner.getUsername());
        } else {
            logger.info("Owner: null");
            return new ResponseEntity(
                new ApiResponse(false, "You're not eligible to fill the review"),
                HttpStatus.BAD_REQUEST);
        }

        List<Review> currentReview = reviewService.getReviewByTransaction(transaction);
        if (currentReview.size() >= 2) {
            logger.info("revienwya udah penuh");
            return new ResponseEntity(new ApiResponse(false, "Review reached max already"),
                HttpStatus.BAD_REQUEST);
        }

        if (currentReview.size() != 0) {
            for (Review eachReview : currentReview) {
                if (eachReview.getUser().getId() == user.getId()) {

                    logger.info("reviewnya udah ada");
                    return new ResponseEntity(new ApiResponse(false, "Review already done before"),
                        HttpStatus.BAD_REQUEST);
                }

            }
        }

        if (transaction != null && owner != null) {
            logger.info("start generate review");
            Review review = new Review();
            review.setRating(addReviewRequest.getRating());
            review.setReview(addReviewRequest.getReview());
            review.setTransaction(transaction);
            review.setUser(user);

            if (reviewService.addReview(review) != null) {

                logger.info("udah berhasil save review");
                int rating = 0;
                int count = 0;

                List<Review> reviews = reviewService.getReviewByUser(owner);

                if (reviews != null) {
                    for (Review allReview : reviews) {
                        rating += allReview.getRating();
                        count++;
                    }

                    double result = 0;
                    if (count != 0) {
                        result = rating / count;
                    } else {
                        result = rating;
                    }

                    owner.setRating((int) result);
                } else {
                    owner.setRating(rating);
                }

                userService.saveUser(owner);

                logger.info("udah berhasil save di usernya");

                transaction.setStatus(StatusEnum.REVIEWED);
                transactionService.saveTransaction(transaction);

                logger.info("udah berhasil save di transactionnya");

                return new ResponseEntity<>(new ApiResponse(true, "Success add review"),
                    HttpStatus.OK);
            }
        }
        logger.info("gagal checking");
        return new ResponseEntity<>(new ApiResponse(false, "Failed add review"),
            HttpStatus.BAD_REQUEST);
    }
}
