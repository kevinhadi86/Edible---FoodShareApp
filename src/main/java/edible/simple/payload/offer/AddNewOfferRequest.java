/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.offer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Hadinata
 * @version $Id: AddNewOfferRequest.java, v 0.1 2019‐09‐18 18:28 Kevin Hadinata Exp $$
 */
public class AddNewOfferRequest {

    private String       category;
    private String       title;
    private String       description;
    private String       unit;
    private Float        quantity;
    private String       expiryTime;
    private List<String> imageUrl = new ArrayList<>();
    private boolean      isCod;
    private String       codDescription;
    private boolean      isDelivery;
    private String       deliveryDescription;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isCod() {
        return isCod;
    }

    public void setCod(boolean cod) {
        isCod = cod;
    }

    public String getCodDescription() {
        return codDescription;
    }

    public void setCodDescription(String codDescription) {
        this.codDescription = codDescription;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    public String getDeliveryDescription() {
        return deliveryDescription;
    }

    public void setDeliveryDescription(String deliveryDescription) {
        this.deliveryDescription = deliveryDescription;
    }
}
