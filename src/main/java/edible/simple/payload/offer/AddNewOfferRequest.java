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

    private String category;
    private String title;
    private String description;
    private Float unit;
    private String expirytime;
    private List<String> imageurl = new ArrayList<>();

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

    public Float getUnit() {
        return unit;
    }

    public void setUnit(Float unit) {
        this.unit = unit;
    }

    public String getExpirytime() {
        return expirytime;
    }

    public void setExpirytime(String expirytime) {
        this.expirytime = expirytime;
    }

    public List<String> getImageurl() {
        return imageurl;
    }

    public void setImageurl(List<String> imageurl) {
        this.imageurl = imageurl;
    }
}
