/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.transcation;

import edible.simple.payload.offer.OtherUserOfferResponse;
import edible.simple.payload.user.BaseUserResponse;

/**
 * @author Kevin Hadinata
 * @version $Id: TransactionResponse.java, v 0.1 2019‐09‐22 22:29 Kevin Hadinata Exp $$
 */
public class TransactionResponse {

    private Long id;
    private String status;
    private Float quantity;
    private String unit;
    private BaseUserResponse user;
    private OtherUserOfferResponse offer;
    private String pickupTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BaseUserResponse getUser() {
        return user;
    }

    public void setUser(BaseUserResponse user) {
        this.user = user;
    }

    public OtherUserOfferResponse getOffer() {
        return offer;
    }

    public void setOffer(OtherUserOfferResponse offer) {
        this.offer = offer;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
}
