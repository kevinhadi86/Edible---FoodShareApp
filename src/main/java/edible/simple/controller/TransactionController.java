/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import edible.simple.model.Offer;
import edible.simple.model.Transaction;
import edible.simple.model.User;
import edible.simple.model.dataEnum.StatusEnum;
import edible.simple.payload.ApiResponse;
import edible.simple.payload.offer.OfferResponse;
import edible.simple.payload.transcation.AddTransactionRequest;
import edible.simple.payload.transcation.TransactionResponse;
import edible.simple.payload.transcation.UpdateTransactionStatusRequest;
import edible.simple.payload.user.BaseUserResponse;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.OfferService;
import edible.simple.service.TransactionService;
import edible.simple.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/myTransaction")
    public List<TransactionResponse> getMyTransaction(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUserById(userPrincipal.getId());
        List<Transaction> transactions = transactionService.getTransactionByUser(user);
        List<TransactionResponse> myTransaction = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setId(transaction.getId());
            transactionResponse.setStatus(transaction.getStatus());
            transactionResponse.setUnit(transaction.getUnit());
            transactionResponse.setTakentime(transaction.getTakentime());

            BaseUserResponse userResponse = new BaseUserResponse();
            BeanUtils.copyProperties(transaction.getUser(), userResponse);

            transactionResponse.setUser(userResponse);

            OfferResponse offerResponse = new OfferResponse();
            fillOfferResponse(offerResponse, transaction);

            transactionResponse.setOffer(offerResponse);

            myTransaction.add(transactionResponse);
        }
        return myTransaction;
    }

    @PostMapping("/accepted")
    public ResponseEntity<ApiResponse> updateTransactionToAccepted(@CurrentUser UserPrincipal userPrincipal,
                                                                   @RequestBody UpdateTransactionStatusRequest request) {
        Transaction transaction = transactionService.getTransactionById(request.getId());
        if (transaction != null && transaction.getStatus()!= StatusEnum.DONE  && transaction.getOffer().getUser().getId() == userPrincipal.getId()) {
            transaction.setStatus(StatusEnum.ACCEPTED);
            if (transactionService.saveTransaction(transaction) != null) {
                Offer offer = offerService.getOfferById(transaction.getOffer().getId());
                offer.setUnit(offer.getUnit() - transaction.getUnit());
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
        if (transaction != null&& transaction.getStatus()!= StatusEnum.DONE  && transaction.getOffer().getUser().getId() == userPrincipal.getId()) {
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
        if (transaction != null && transaction.getStatus()!= StatusEnum.DONE && transaction.getStatus()== StatusEnum.ACCEPTED && transaction.getOffer().getUser().getId() != userPrincipal.getId()) {
            transaction.setStatus(StatusEnum.DONE);
            transaction.setTakentime(new Date());
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
        if (transaction != null && transaction.getStatus()!= StatusEnum.DONE && transaction.getOffer().getUser().getId() == userPrincipal.getId()) {
            transaction.setStatus(StatusEnum.ONGOING);
            if (transactionService.saveTransaction(transaction) != null) {
                return new ResponseEntity<>(new ApiResponse(true, "Success save transaction"),
                    HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponse(false, "Failed save transaction"),
            HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTransaction(@CurrentUser UserPrincipal userPrincipal,
                                                      @RequestBody AddTransactionRequest request) {
        Offer offer = offerService.getOfferById(request.getOffer_id());
        if (userPrincipal.getId() != offer.getUser().getId() && offer != null
            && request.getUnit() != 0 && offer.getUnit() - request.getUnit() > 0) {
            Transaction transaction = new Transaction();
            transaction.setUser(userService.getUserById(userPrincipal.getId()));
            transaction.setOffer(offer);
            transaction.setUnit(request.getUnit());
            transaction.setStatus(StatusEnum.INIT);
            if (transactionService.saveTransaction(transaction) != null) {
                return new ResponseEntity(new ApiResponse(true, "Success add transaction"),
                    HttpStatus.OK);
            }
        }
        return new ResponseEntity(new ApiResponse(false, "Failed add transaction"),
            HttpStatus.BAD_REQUEST);
    }

    private void fillOfferResponse(OfferResponse offerResponse, Transaction transaction) {
        BeanUtils.copyProperties(transaction.getOffer(), offerResponse);

        BaseUserResponse baseUserResponse = new BaseUserResponse();
        BeanUtils.copyProperties(transaction.getOffer().getUser(), baseUserResponse);
        offerResponse.setUser(baseUserResponse);

        offerResponse.setLocation(transaction.getOffer().getUser().getLocation().getName());
    }

}
