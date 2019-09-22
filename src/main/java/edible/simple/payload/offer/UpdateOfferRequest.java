/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.offer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Hadinata
 * @version $Id: UpdateOfferRequest.java, v 0.1 2019‐09‐21 18:30 Kevin Hadinata Exp $$
 */
public class UpdateOfferRequest {

    private Long id;
    private String category;
    private String title;
    private String description;
    private Float unit;
    private String expirytime;
    private List<String> imageurl = new ArrayList<>();

    /**
     * Getter method for property id.
     *
     * @return property value of id
     **/
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property id.
     *
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property category.
     *
     * @return property value of category
     **/
    public String getCategory() {
        return category;
    }

    /**
     * Setter method for property category.
     *
     * @param category value to be assigned to property category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter method for property title.
     *
     * @return property value of title
     **/
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for property title.
     *
     * @param title value to be assigned to property title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for property description.
     *
     * @return property value of description
     **/
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for property description.
     *
     * @param description value to be assigned to property description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for property unit.
     *
     * @return property value of unit
     **/
    public Float getUnit() {
        return unit;
    }

    /**
     * Setter method for property unit.
     *
     * @param unit value to be assigned to property unit
     */
    public void setUnit(Float unit) {
        this.unit = unit;
    }

    /**
     * Getter method for property expirytime.
     *
     * @return property value of expirytime
     **/
    public String getExpirytime() {
        return expirytime;
    }

    /**
     * Setter method for property expirytime.
     *
     * @param expirytime value to be assigned to property expirytime
     */
    public void setExpirytime(String expirytime) {
        this.expirytime = expirytime;
    }

    /**
     * Getter method for property imageurl.
     *
     * @return property value of imageurl
     **/
    public List<String> getImageurl() {
        return imageurl;
    }

    /**
     * Setter method for property imageurl.
     *
     * @param imageurl value to be assigned to property imageurl
     */
    public void setImageurl(List<String> imageurl) {
        this.imageurl = imageurl;
    }
}
