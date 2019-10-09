/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edible.simple.model.Transaction;
import edible.simple.model.User;
import edible.simple.repository.TransactionRepository;

/**
 * @author Kevin Hadinata
 * @version $Id: TransactionServiceImpl.java, v 0.1 2019‐09‐22 22:10 Kevin Hadinata Exp $$
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getByOfferUser(User user) {
        return transactionRepository.findAllByOffer_User(user);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if(transaction.isPresent()){
            return transaction.get();
        }
        return null;
    }

    @Override
    public List<Transaction> getTransactionByUser(User user) {
        return transactionRepository.findAllByUser(user);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
