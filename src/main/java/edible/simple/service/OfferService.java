/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.List;
import java.util.Set;

import edible.simple.model.Category;
import edible.simple.model.Offer;
import edible.simple.model.OfferImage;
import edible.simple.model.User;

/**
 * @author Kevin Hadinata
 * @version $Id: OfferService.java, v 0.1 2019‐09‐18 17:58 Kevin Hadinata Exp $$
 */
public interface OfferService {

    public Offer saveOffer(Offer offer);

    public Offer saveOfferImages(Offer offer, Set<OfferImage> offerImages);

    public Offer getOfferById(Long id);

    public List<Offer> getAllOffer();

    public List<Offer> getOfferByUser(User user);

    public List<Offer> getOfferByCategory(Category category);

    public boolean deleteOffer(Offer offer);

    public boolean deleteOfferImage(Offer offer);

}