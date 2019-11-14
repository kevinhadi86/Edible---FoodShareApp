/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import edible.simple.model.Offer;
import edible.simple.model.OfferImage;
import edible.simple.model.Transaction;
import edible.simple.model.User;
import edible.simple.model.dataEnum.StatusEnum;
import edible.simple.payload.ApiResponse;
import edible.simple.payload.offer.OtherUserOfferResponse;
import edible.simple.payload.transcation.AddTransactionRequest;
import edible.simple.payload.transcation.TransactionResponse;
import edible.simple.payload.transcation.UpdateTransactionStatusRequest;
import edible.simple.payload.user.BaseUserResponse;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.OfferService;
import edible.simple.service.TransactionService;
import edible.simple.service.UnitService;
import edible.simple.service.UserService;

/**
 * @author Kevin Hadinata
 * @version $Id: TransactionController.java, v 0.1 2019‐09‐22 22:04 Kevin Hadinata Exp $$
 */
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService        userService;

    @Autowired
    OfferService       offerService;

    @Autowired
    UnitService        unitService;

    static Logger      logger = LoggerFactory.getLogger(TransactionController.class);

    @GetMapping("/myTransaction/take")
    public List<TransactionResponse> getMyTransactionAsTaker(@CurrentUser UserPrincipal userPrincipal) {

        User user = userService.getUserById(userPrincipal.getId());

        List<Transaction> transactions = transactionService.getTransactionByUser(user);
        List<TransactionResponse> myTransaction = new ArrayList<>();

        for (Transaction transaction : transactions) {

            TransactionResponse transactionResponse = new TransactionResponse();

            transactionResponse.setId(transaction.getId());
            transactionResponse.setStatus(transaction.getStatus().name());
            transactionResponse.setUnit(transaction.getUnit().getUnitName().name());
            transactionResponse.setQuantity(transaction.getQuantity());
            transactionResponse
                .setPickupTime(new SimpleDateFormat("HH:mm").format(transaction.getPickupTime()));

            BaseUserResponse userResponse = new BaseUserResponse();
            BeanUtils.copyProperties(transaction.getUser(), userResponse);

            transactionResponse.setUser(userResponse);

            OtherUserOfferResponse otherUserOfferResponse = new OtherUserOfferResponse();
            fillOfferResponse(otherUserOfferResponse, transaction);

            transactionResponse.setOffer(otherUserOfferResponse);

            myTransaction.add(transactionResponse);
        }
        return myTransaction;
    }

    @GetMapping("/myTransaction")
    public List<TransactionResponse> getMyTransaction(@CurrentUser UserPrincipal userPrincipal) {

        User user = userService.getUserById(userPrincipal.getId());

        List<Transaction> transactions = transactionService.getByOfferUser(user);
        List<TransactionResponse> myTransaction = new ArrayList<>();

        for (Transaction transaction : transactions) {

            if (transaction.getOffer().getUser().equals(user)) {
                TransactionResponse transactionResponse = new TransactionResponse();

                transactionResponse.setId(transaction.getId());
                transactionResponse.setStatus(transaction.getStatus().name());
                transactionResponse.setUnit(transaction.getUnit().getUnitName().name());
                transactionResponse.setQuantity(transaction.getQuantity());
                transactionResponse.setPickupTime(
                    new SimpleDateFormat("HH:mm").format(transaction.getPickupTime()));

                BaseUserResponse userResponse = new BaseUserResponse();
                BeanUtils.copyProperties(transaction.getUser(), userResponse);

                transactionResponse.setUser(userResponse);

                OtherUserOfferResponse otherUserOfferResponse = new OtherUserOfferResponse();
                fillOfferResponse(otherUserOfferResponse, transaction);

                transactionResponse.setOffer(otherUserOfferResponse);

                myTransaction.add(transactionResponse);
            }
        }
        return myTransaction;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTransaction(@CurrentUser UserPrincipal userPrincipal,
                                                      @RequestBody AddTransactionRequest request) {

        Offer offer = offerService.getOfferById(request.getOffer_id());
        boolean userCheck = userPrincipal.getId() != offer.getUser().getId();
        if (userCheck && offer != null && checkTakeTransaction(request, offer)) {

            Transaction transaction = new Transaction();
            transaction.setUser(userService.getUserById(userPrincipal.getId()));
            transaction.setOffer(offer);
            transaction.setQuantity(request.getQuantity());
            transaction.setUnit(offer.getUnit());

            transaction.setStatus(StatusEnum.INIT);

            Date pickupTime = new Date();
            try {
                pickupTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .parse(request.getPickupTime());
            } catch (ParseException e) {
                logger.info("Failed when save transaction data, because: " + e);
            }
            transaction.setPickupTime(pickupTime);

            if (transactionService.saveTransaction(transaction) != null) {
                logger.info("Success when save transaction data");

                return new ResponseEntity(new ApiResponse(true, "Success add transaction"),
                    HttpStatus.OK);
            } else {
                logger.info("Failed when save transaction data");
            }

        }
        logger.info("Failed when save transaction data because checking, userCheck:" + userCheck
                    + "offer: " + offer.toString());
        return new ResponseEntity(new ApiResponse(false, "Failed add transaction"),
            HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/accepted")
    public ResponseEntity<ApiResponse> updateTransactionToAccepted(@CurrentUser UserPrincipal userPrincipal,
                                                                   @RequestBody UpdateTransactionStatusRequest request) {
        Transaction transaction = transactionService.getTransactionById(request.getId());
        if (transaction != null && transaction.getStatus() != StatusEnum.DONE
            && transaction.getOffer().getUser().getId() == userPrincipal.getId()
            && transaction.getStatus() != StatusEnum.REJECTED) {

            transaction.setStatus(StatusEnum.ACCEPTED);

            if (transactionService.saveTransaction(transaction) != null) {

                Offer offer = offerService.getOfferById(transaction.getOffer().getId());
                offer.setQuantity(offer.getQuantity() - transaction.getQuantity());

                if (offerService.saveOffer(offer) != null) {

                    return new ResponseEntity<>(new ApiResponse(true, "Success save transaction"),
                        HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(new ApiResponse(false, "Failed save transaction"),
            HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/rejected")
    public ResponseEntity<ApiResponse> updateTransactionToRejected(@CurrentUser UserPrincipal userPrincipal,
                                                                   @RequestBody UpdateTransactionStatusRequest request) {
        Transaction transaction = transactionService.getTransactionById(request.getId());
        if (transaction != null && transaction.getStatus() != StatusEnum.DONE
            && transaction.getOffer().getUser().getId() == userPrincipal.getId()) {

            transaction.setStatus(StatusEnum.REJECTED);

            if (transactionService.saveTransaction(transaction) != null) {
                return new ResponseEntity<>(new ApiResponse(true, "Success save transaction"),
                    HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponse(false, "Failed save transaction"),
            HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/done")
    public ResponseEntity<ApiResponse> updateTranscationToDone(@CurrentUser UserPrincipal userPrincipal,
                                                               @RequestBody UpdateTransactionStatusRequest request) {
        Transaction transaction = transactionService.getTransactionById(request.getId());
        if (transaction != null && transaction.getStatus() != StatusEnum.DONE
            && transaction.getStatus() == StatusEnum.ACCEPTED
            && transaction.getOffer().getUser().getId() != userPrincipal.getId()) {

            transaction.setStatus(StatusEnum.DONE);
            transaction.setPickupTime(new Date());

            if (transactionService.saveTransaction(transaction) != null) {
                return new ResponseEntity<>(new ApiResponse(true, "Success save transaction"),
                    HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponse(false, "Failed save transaction"),
            HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/ongoing")
    public ResponseEntity<ApiResponse> updateTranscationToOngoing(@CurrentUser UserPrincipal userPrincipal,
                                                                  @RequestBody UpdateTransactionStatusRequest request) {
        Transaction transaction = transactionService.getTransactionById(request.getId());
        if (transaction != null && transaction.getStatus() != StatusEnum.DONE
            && transaction.getOffer().getUser().getId() == userPrincipal.getId()) {

            transaction.setStatus(StatusEnum.ONGOING);

            if (transactionService.saveTransaction(transaction) != null) {
                return new ResponseEntity<>(new ApiResponse(true, "Success save transaction"),
                    HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponse(false, "Failed save transaction"),
            HttpStatus.BAD_REQUEST);
    }

    private void fillOfferResponse(OtherUserOfferResponse otherUserOfferResponse,
                                   Transaction transaction) {

        BeanUtils.copyProperties(transaction.getOffer(), otherUserOfferResponse);
        otherUserOfferResponse.setUnit(transaction.getUnit().getUnitName().name());
        otherUserOfferResponse.setExpiryDate(
            new SimpleDateFormat("yyyy-MM-dd").format(transaction.getOffer().getExpiryDate()));

        List<String> imageUrls = new ArrayList<>();
        for (OfferImage offerImage : transaction.getOffer().getOfferImages()) {
            imageUrls.add(offerImage.getUrl());
        }
        otherUserOfferResponse.setImageUrls(imageUrls);

        BaseUserResponse baseUserResponse = new BaseUserResponse();
        BeanUtils.copyProperties(transaction.getOffer().getUser(), baseUserResponse);
        otherUserOfferResponse.setUser(baseUserResponse);

        otherUserOfferResponse.setLocation(transaction.getOffer().getUser().getCity());
    }

    private boolean checkTakeTransaction(AddTransactionRequest request, Offer offer) {
        Date now = new Date();
        if (request.getQuantity() != 0 && offer.getQuantity() - request.getQuantity() >= 0
            && offer.getExpiryDate().compareTo(now) < 0) {
            return true;
        }
        logger
            .info("Failed when save transaction data when checking the take transaction, "
                  + request.getQuantity() + "," + (offer.getQuantity() - request.getQuantity() >= 0)
                  + "," + (offer.getExpiryDate().compareTo(now) < 0));
        return false;
    }

}
