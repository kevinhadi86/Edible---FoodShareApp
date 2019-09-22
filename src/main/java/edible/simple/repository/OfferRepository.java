/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import edible.simple.model.Category;
import edible.simple.model.Offer;
import edible.simple.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Kevin Hadinata
 * @version $Id: OfferRepository.java, v 0.1 2019‐09‐18 17:58 Kevin Hadinata Exp $$
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    public List<Offer> getAllByUser(User user);

    public List<Offer> getAllByCategory(Category category);
}