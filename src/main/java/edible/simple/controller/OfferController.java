/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edible.simple.model.*;
import edible.simple.model.dataEnum.CategoryName;
import edible.simple.model.dataEnum.UnitName;
import edible.simple.payload.ApiResponse;
import edible.simple.payload.offer.*;
import edible.simple.payload.user.BaseUserResponse;
import edible.simple.security.CurrentUser;
import edible.simple.security.UserPrincipal;
import edible.simple.service.*;

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

    @Autowired
    UnitService     unitService;

    @Autowired
    StorageService  storageService;

    @GetMapping("/{id}")
    public OtherUserOfferResponse getOfferById(@PathVariable String id) {

        Offer offer = offerService.getOfferById(Long.parseLong(id));

        OtherUserOfferResponse otherUserOfferResponse = new OtherUserOfferResponse();
        fillOtherUserOfferResponse(otherUserOfferResponse, offer);

        return otherUserOfferResponse;
    }

    @GetMapping("/myOffer")
    public List<BaseOfferResponse> getOfferByUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUserById(userPrincipal.getId());

        List<Offer> offers = offerService.getOfferByUser(user);
        List<BaseOfferResponse> myOffers = new ArrayList<>();

        for (Offer offer : offers) {

            BaseOfferResponse baseOfferResponse = new BaseOfferResponse();

            fillBaseOfferResponse(baseOfferResponse, offer);

            myOffers.add(baseOfferResponse);
        }

        return myOffers;
    }

    @GetMapping("/all")
    public List<OtherUserOfferResponse> getAllOffer(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUserById(userPrincipal.getId());

        List<Offer> offers = offerService.getAllOffer();
        List<OtherUserOfferResponse> allOffer = new ArrayList<>();

        for (Offer offer : offers) {

            if (offer.getUser().getId() != user.getId()) {

                OtherUserOfferResponse otherUserOfferResponse = new OtherUserOfferResponse();

                fillOtherUserOfferResponse(otherUserOfferResponse, offer);

                allOffer.add(otherUserOfferResponse);
            }
        }

        return allOffer;
    }

    @GetMapping("category/{category}")
    public List<OtherUserOfferResponse> getOfferByCategory(@PathVariable String category) {

        Category searchCategory = categoryService
            .getCategoryByName(CategoryName.valueOf(category.toUpperCase()));

        List<Offer> offers = offerService.getOfferByCategory(searchCategory);
        List<OtherUserOfferResponse> offersByCategory = new ArrayList<>();

        for (Offer offer : offers) {

            OtherUserOfferResponse otherUserOfferResponse = new OtherUserOfferResponse();

            fillOtherUserOfferResponse(otherUserOfferResponse, offer);

            offersByCategory.add(otherUserOfferResponse);
        }

        return offersByCategory;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNewOffer(@CurrentUser UserPrincipal userPrincipal,
                                                   @RequestBody AddNewOfferRequest addNewOfferRequest,
                                                   HttpServletRequest request) {

        User user = userService.getUserById(userPrincipal.getId());

        Offer offer = new Offer();

        offer.setUser(user);

        if (checkOfferRequest(addNewOfferRequest)) {
            return new ResponseEntity(new ApiResponse(false, "Category or Unit do not exists"),
                HttpStatus.BAD_REQUEST);
        }
        fillOfferRequest(offer, addNewOfferRequest);

        Offer newOffer = offerService.saveOffer(offer);

        Set<OfferImage> offerImages = new HashSet<>();
        fillOfferImages(offerImages, newOffer, addNewOfferRequest, request);

        offerService.saveOfferImages(offer, offerImages);

        if (newOffer == null) {
            return new ResponseEntity(new ApiResponse(false, "Failed add new offer"),
                HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(new ApiResponse(true, "Success add new offer"), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateOffer(@RequestBody UpdateOfferRequest updateOfferRequest, HttpServletRequest request) {

        Offer offer = offerService.getOfferById(updateOfferRequest.getId());

        if (checkOfferRequest(updateOfferRequest)) {
            return new ResponseEntity(new ApiResponse(false, "Category or Unit do not exists"),
                HttpStatus.BAD_REQUEST);
        }

        fillOfferRequest(offer, updateOfferRequest);

        if (offerService.saveOffer(offer) == null) {
            return new ResponseEntity(new ApiResponse(false, "fail to update"),
                HttpStatus.BAD_REQUEST);
        }

        if(updateOfferRequest.getImageUrl()!=null){
            Set<OfferImage> offerImages = new HashSet<>();
            fillOfferImages(offerImages, offer, updateOfferRequest, request);

            if (offerService.saveOfferImages(offer, offerImages) == null) {
                return new ResponseEntity(new ApiResponse(false, "fail to update image"),
                        HttpStatus.BAD_REQUEST);
            }

        }

        return new ResponseEntity(new ApiResponse(true, "success to update"), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteOffer(@CurrentUser UserPrincipal userPrincipal,
                                                   @RequestBody DeleteOfferRequest deleteOfferRequest) {
        Offer offer = offerService.getOfferById(deleteOfferRequest.getId());

        if (offer.getUser().getId() == userPrincipal.getId()) {
            offerService.deleteOfferImage(offer);
            offerService.deleteOffer(offer);
            return new ResponseEntity(new ApiResponse(true, "Success delete offer"), HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Failed delete offer"),
            HttpStatus.BAD_REQUEST);
    }

    private boolean checkOfferRequest(AddNewOfferRequest addNewOfferRequest) {

        CategoryName categoryName = CategoryName.valueOf(addNewOfferRequest.getCategory());
        if (categoryName == null) {
            return true;
        }

        UnitName unitName = UnitName.valueOf(addNewOfferRequest.getUnit());
        if (unitName == null) {
            return true;
        }

        return false;
    }

    private void fillBaseOfferResponse(BaseOfferResponse baseOfferResponse, Offer offer) {

        baseOfferResponse.setId(offer.getId());
        baseOfferResponse.setCategoryName(offer.getCategory().getName().name());
        baseOfferResponse.setTitle(offer.getTitle());
        baseOfferResponse.setDescription(offer.getDescription());
        baseOfferResponse.setQuantity(offer.getQuantity());
        baseOfferResponse.setUnit(offer.getUnit().getName().name());
        baseOfferResponse
            .setExpiryDate(new SimpleDateFormat("yyyy-MM-dd").format(offer.getExpirytime()));
        List<String> imageUrls = new ArrayList<>();
        for (OfferImage offerImage : offer.getOfferImages()) {
            imageUrls.add(offerImage.getUrl());
        }
        baseOfferResponse.setImageUrls(imageUrls);

    }

    private void fillOtherUserOfferResponse(OtherUserOfferResponse otherUserOfferResponse,
                                            Offer offer) {

        fillBaseOfferResponse(otherUserOfferResponse, offer);

        BaseUserResponse baseUserResponse = new BaseUserResponse();
        BeanUtils.copyProperties(offer.getUser(), baseUserResponse);
        otherUserOfferResponse.setUser(baseUserResponse);

        otherUserOfferResponse.setLocation(offer.getUser().getLocation().getName());
    }

    private void fillOfferRequest(Offer offer, AddNewOfferRequest addNewOfferRequest) {

        CategoryName categoryName = CategoryName.valueOf(addNewOfferRequest.getCategory());
        Category category = categoryService.getCategoryByName(categoryName);
        offer.setCategory(category);

        UnitName unitName = UnitName.valueOf(addNewOfferRequest.getUnit());
        Unit unit = unitService.getUnitByName(unitName);
        offer.setUnit(unit);

        offer.setTitle(addNewOfferRequest.getTitle());
        offer.setDescription(addNewOfferRequest.getDescription());
        offer.setQuantity(addNewOfferRequest.getQuantity());

        Date expiryTime = null;
        try {
            expiryTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .parse(addNewOfferRequest.getExpiryTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        offer.setExpirytime(expiryTime);
    }

    private void fillOfferImages(Set<OfferImage> offerImages, Offer offer,
                                 AddNewOfferRequest addNewOfferRequest,
                                 HttpServletRequest request) {



        for (String url : addNewOfferRequest.getImageUrl()) {

            String baseUrl = String.format("%s://%s:%d/api/image/files/", request.getScheme(),
                    request.getServerName(), request.getServerPort());
            String imageUrl = storageService.store(url, baseUrl);

            OfferImage offerImage = new OfferImage(offer, imageUrl);
            offerImages.add(offerImage);
        }
    }

}
