/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edible.simple.model.Transaction;
import edible.simple.model.User;

/**
 * @author Kevin Hadinata
 * @version $Id: TransactionRepository.java, v 0.1 2019‐09‐22 20:49 Kevin Hadinata Exp $$
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findAllByOffer_User(User user);

    List<Transaction> findAllByUser(User user);
}