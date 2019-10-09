/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edible.simple.model.OfferImage;

/**
 * @author Kevin Hadinata
 * @version $Id: OfferImageRepository.java, v 0.1 2019‐09‐19 13:31 Kevin Hadinata Exp $$
 */
public interface OfferImageRepository extends JpaRepository<OfferImage,Long> {
}