/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edible.simple.model.Category;
import edible.simple.model.Offer;
import edible.simple.model.OfferImage;
import edible.simple.model.User;
import edible.simple.repository.OfferImageRepository;
import edible.simple.repository.OfferRepository;

/**
 * @author Kevin Hadinata
 * @version $Id: OfferServiceImpl.java, v 0.1 2019‐09‐18 17:59 Kevin Hadinata Exp $$
 */
@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    OfferImageRepository offerImageRepository;

    @Override
    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Offer saveOfferImages(Offer offer, Set<OfferImage> offerImages) {
        if(offer.getOfferImages()!=null){
            offerImageRepository.deleteAll(offer.getOfferImages());
        }
        offerImageRepository.saveAll(offerImages);
        return offer;
    }

    @Override
    public Offer getOfferById(Long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        if(offer.isPresent()){
            return offer.get();
        }
        return null;
    }

    @Override
    public List<Offer> getAllOffer() {
        return offerRepository.getAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Offer> getOfferByUser(User user) {
        return offerRepository.getAllByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public List<Offer> getOfferByCategory(Category category) {
        return offerRepository.getAllByCategoryOrderByCreatedAtDesc(category);
    }

    @Override
    public List<Offer> getOfferByTitle(String title) {
        return offerRepository.getAllByTitleContaining(title);
    }

    @Override
    public List<Offer> getOfferByDescription(String description) {
        return offerRepository.getAllByDescriptionContaining(description);
    }

    @Override
    public boolean deleteOffer(Offer offer) {
        offerRepository.delete(offer);
        return true;
    }

    @Override
    public boolean deleteOfferImage(Offer offer) {
        Set<OfferImage> offerImages = offer.getOfferImages();
        offerImageRepository.deleteAll(offerImages);
        return true;
    }

}
