/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import edible.simple.model.Offer;
import edible.simple.model.OfferImage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kevin Hadinata
 * @version $Id: OfferImageRepository.java, v 0.1 2019‐09‐19 13:31 Kevin Hadinata Exp $$
 */
public interface OfferImageRepository extends JpaRepository<OfferImage,Long> {
}