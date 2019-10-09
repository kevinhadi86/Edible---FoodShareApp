/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edible.simple.model.Transaction;
import edible.simple.model.User;

/**
 * @author Kevin Hadinata
 * @version $Id: TransactionService.java, v 0.1 2019‐09‐22 20:50 Kevin Hadinata Exp $$
 */
@Service
public interface TransactionService {

    public List<Transaction> getByOfferUser(User user);

    public Transaction getTransactionById(Long id);

    public List<Transaction> getTransactionByUser(User user);

    public Transaction saveTransaction(Transaction transaction);
}