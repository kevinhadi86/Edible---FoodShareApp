/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * @author Kevin Hadinata
 * @version $Id: Offer.java, v 0.1 2019‐09‐18 17:35 Kevin Hadinata Exp $$
 */
@Entity
@Table(name = "offers")
public class Offer extends DataAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    private String title;
    private String description;
    private Float quantity;
    @Column(name = "expiry_date")
    private Date expiryDate;

    @OneToMany(mappedBy = "offer")
    private Set<OfferImage> offerImages = new HashSet<>();

    public Offer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Set<OfferImage> getOfferImages() {
        return offerImages;
    }

    public void setOfferImages(Set<OfferImage> offerImages) {
        this.offerImages = offerImages;
    }

}
