/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edible.simple.model.Category;
import edible.simple.model.Offer;
import edible.simple.model.User;

/**
 * @author Kevin Hadinata
 * @version $Id: OfferRepository.java, v 0.1 2019‐09‐18 17:58 Kevin Hadinata Exp $$
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    public List<Offer> getAllByOrderByCreatedAtDesc();

    public List<Offer> getAllByCategoryOrderByCreatedAtDesc(Category category);

    public List<Offer> getAllByUserOrderByCreatedAtDesc(User user);

}