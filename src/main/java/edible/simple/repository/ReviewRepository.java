/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edible.simple.model.Review;
import edible.simple.model.Transaction;
import edible.simple.model.User;

/**
 * @author Kevin Hadinata
 * @version $Id: ReviewRepository.java, v 0.1 2019‐09‐22 23:45 Kevin Hadinata Exp $$
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findAllByTransactionUser(User user);

    Review findByTransaction(Transaction transaction);
}