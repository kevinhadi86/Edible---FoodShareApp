/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.transcation;

/**
 * @author Kevin Hadinata
 * @version $Id: AddTransactionRequest.java, v 0.1 2019‐09‐22 20:51 Kevin Hadinata Exp $$
 */
public class AddTransactionRequest {

    private Long offer_id;
    private String unit;
    private Float quantity;
    private String pickupTime;

    public Long getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(Long offer_id) {
        this.offer_id = offer_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
}
