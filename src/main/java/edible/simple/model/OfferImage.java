/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author Kevin Hadinata
 * @version $Id: OfferImage.java, v 0.1 2019‐09‐18 18:24 Kevin Hadinata Exp $$
 */
@Entity
@Table(name = "offer_images")
public class OfferImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;
    @NotBlank
    private String url;

    public OfferImage() {
    }

    public OfferImage(Offer offer,@NotBlank String url) {
        this.offer = offer;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
