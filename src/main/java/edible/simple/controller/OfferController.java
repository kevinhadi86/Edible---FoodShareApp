/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import edible.simple.model.OfferImage;
import edible.simple.payload.offer.*;
import edible.simple.payload.user.BaseUserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edible.simple.model.Category;
import edible.simple.model.Offer;
import edible.simple.model.User;
import edible.simple.model.dataEnum.CategoryName;
import edible.simple.payload.ApiResponse;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.CategoryService;
import edible.simple.service.OfferService;
import edible.simple.service.UserService;

/**
 * @author Kevin Hadinata
 * @version $Id: OfferController.java, v 0.1 2019‐09‐18 18:00 Kevin Hadinata Exp $$
 */
@RestController
@RequestMapping("/api/offer")
public class OfferController {

    @Autowired
    UserService     userService;

    @Autowired
    OfferService    offerService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/{id}")
    public OfferResponse getOfferById(@CurrentUser UserPrincipal userPrincipal, @PathVariable String id) {
        User user = userService.getUserById(userPrincipal.getId());

        Offer offer = offerService.getOfferById(Long.parseLong(id));
        OfferResponse offerResponse = new OfferResponse();

        offerResponse.setId(offer.getId());
        offerResponse.setCategoryName(offer.getCategory().getName());
        offerResponse.setTitle(offer.getTitle());
        offerResponse.setDescription(offer.getDescription());
        offerResponse.setUnit(offer.getUnit());
        offerResponse.setExpiryDate(offer.getExpirytime());
        List<String> imageUrls = new ArrayList<>();
        for (OfferImage offerImage : offer.getOfferImages()) {
            imageUrls.add(offerImage.getUrl());
        }
        offerResponse.setImageUrls(imageUrls);

        BaseUserResponse baseUserResponse = new BaseUserResponse();
        BeanUtils.copyProperties(offer.getUser(), baseUserResponse);
        offerResponse.setUser(baseUserResponse);

        offerResponse.setLocation(offer.getUser().getLocation().getName());
        return offerResponse;
    }

    @GetMapping("/myOffer")
    public List<MyOfferResponse> getOfferByUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUserById(userPrincipal.getId());

        List<Offer> offers = offerService.getOfferByUser(user);
        List<MyOfferResponse> myOffers = new ArrayList<>();

        for (Offer offer : offers) {
            MyOfferResponse myOfferResponse = new MyOfferResponse();
            myOfferResponse.setId(offer.getId());
            myOfferResponse.setCategoryName(offer.getCategory().getName());
            myOfferResponse.setTitle(offer.getTitle());
            myOfferResponse.setDescription(offer.getDescription());
            myOfferResponse.setUnit(offer.getUnit());
            myOfferResponse.setExpiryDate(offer.getExpirytime());
            List<String> imageUrls = new ArrayList<>();
            for (OfferImage offerImage : offer.getOfferImages()) {
                imageUrls.add(offerImage.getUrl());
            }
            myOfferResponse.setImageUrls(imageUrls);
            myOffers.add(myOfferResponse);
        }
        return myOffers;
    }

    @GetMapping("category/{category}")
    public List<OfferResponse> getOfferByUser(@PathVariable String category) {

        Category searchCategory = categoryService
            .getCategoryByName(CategoryName.valueOf(category.toUpperCase()));

        List<Offer> offers = offerService.getOfferByCategory(searchCategory);
        List<OfferResponse> offersByCategory = new ArrayList<>();

        for (Offer offer : offers) {
            OfferResponse offerResponse = new OfferResponse();
            offerResponse.setId(offer.getId());
            offerResponse.setCategoryName(offer.getCategory().getName());
            offerResponse.setTitle(offer.getTitle());
            offerResponse.setDescription(offer.getDescription());
            offerResponse.setUnit(offer.getUnit());
            offerResponse.setExpiryDate(offer.getExpirytime());
            List<String> imageUrls = new ArrayList<>();
            for (OfferImage offerImage : offer.getOfferImages()) {
                imageUrls.add(offerImage.getUrl());
            }
            offerResponse.setImageUrls(imageUrls);

            BaseUserResponse baseUserResponse = new BaseUserResponse();
            BeanUtils.copyProperties(offer.getUser(), baseUserResponse);
            offerResponse.setUser(baseUserResponse);

            offerResponse.setLocation(offer.getUser().getLocation().getName());

            offersByCategory.add(offerResponse);

        }
        return offersByCategory;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNewOffer(@CurrentUser UserPrincipal userPrincipal,
                                                   @RequestBody AddNewOfferRequest addNewOfferRequest) {

        User user = userService.getUserById(userPrincipal.getId());

        Offer offer = new Offer();
        offer.setUser(user);

        CategoryName categoryName = CategoryName.valueOf(addNewOfferRequest.getCategory());
        if (categoryName == null) {
            return new ResponseEntity(new ApiResponse(false, "Category not exists"),
                HttpStatus.BAD_REQUEST);
        }
        Category category = categoryService
            .getCategoryByName(CategoryName.valueOf(addNewOfferRequest.getCategory()));
        offer.setCategory(category);

        offer.setTitle(addNewOfferRequest.getTitle());
        offer.setDescription(addNewOfferRequest.getDescription());
        offer.setUnit(addNewOfferRequest.getUnit());

        Date expiryTime = null;
        try {
            expiryTime = new SimpleDateFormat("dd/MM/yyyy")
                .parse(addNewOfferRequest.getExpirytime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        offer.setExpirytime(expiryTime);

        Offer newOffer = offerService.saveOffer(offer);

        Set<OfferImage> offerImages = new HashSet<>();
        for (String url : addNewOfferRequest.getImageurl()) {
            OfferImage offerImage = new OfferImage(offer, url);
            offerImages.add(offerImage);
        }
        offerService.saveOfferImages(offer, offerImages);

        if (newOffer == null) {
            return new ResponseEntity(new ApiResponse(false, "Failed add new offer"),
                HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(new ApiResponse(true, "Success add new offer"), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateOffer(@RequestBody UpdateOfferRequest updateOfferRequest) {
        Offer offer = offerService.getOfferById(updateOfferRequest.getId());

        CategoryName categoryName = CategoryName.valueOf(updateOfferRequest.getCategory());
        if (categoryName == null) {
            return new ResponseEntity(new ApiResponse(false, "Category not exists"),
                    HttpStatus.BAD_REQUEST);
        }
        Category category = categoryService
                .getCategoryByName(CategoryName.valueOf(updateOfferRequest.getCategory()));
        offer.setCategory(category);

        offer.setTitle(updateOfferRequest.getTitle());
        offer.setDescription(updateOfferRequest.getDescription());
        offer.setUnit(updateOfferRequest.getUnit());

        Date expiryTime = null;
        try {
            expiryTime = new SimpleDateFormat("dd/MM/yyyy")
                    .parse(updateOfferRequest.getExpirytime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        offer.setExpirytime(expiryTime);

        if(offerService.saveOffer(offer) == null){
            return new ResponseEntity(new ApiResponse(false,"fail to update"),HttpStatus.BAD_REQUEST);
        }

        Set<OfferImage> offerImages = new HashSet<>();
        for (String url : updateOfferRequest.getImageurl()) {
            OfferImage offerImage = new OfferImage(offer, url);
            offerImages.add(offerImage);
        }

        if(offerService.saveOfferImages(offer,offerImages)==null){
            return new ResponseEntity(new ApiResponse(false,"fail to update image"),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(new ApiResponse(true,"success to update"),HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteOffer(@CurrentUser UserPrincipal userPrincipal, @RequestBody DeleteOfferRequest deleteOfferRequest){
        Offer offer = offerService.getOfferById(deleteOfferRequest.getId());

        if(offer.getUser().getId() == userPrincipal.getId()){
            offerService.deleteOfferImage(offer);
            offerService.deleteOffer(offer);
            return new ResponseEntity(new ApiResponse(true, "Success delete offer"), HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Failed delete offer"), HttpStatus.BAD_REQUEST);
    }

}
