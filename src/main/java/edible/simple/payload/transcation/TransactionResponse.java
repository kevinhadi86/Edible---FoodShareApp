/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.transcation;

import edible.simple.model.dataEnum.StatusEnum;
import edible.simple.payload.offer.OfferResponse;
import edible.simple.payload.user.BaseUserResponse;

import java.util.Date;

/**
 * @author Kevin Hadinata
 * @version $Id: TransactionResponse.java, v 0.1 2019‐09‐22 22:29 Kevin Hadinata Exp $$
 */
public class TransactionResponse {

    private Long id;
    private StatusEnum status;
    private Float unit;
    private BaseUserResponse user;
    private OfferResponse offer;
    private Date takentime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Float getUnit() {
        return unit;
    }

    public void setUnit(Float unit) {
        this.unit = unit;
    }

    public BaseUserResponse getUser() {
        return user;
    }

    public void setUser(BaseUserResponse user) {
        this.user = user;
    }

    public OfferResponse getOffer() {
        return offer;
    }

    public void setOffer(OfferResponse offer) {
        this.offer = offer;
    }

    public Date getTakentime() {
        return takentime;
    }

    public void setTakentime(Date takentime) {
        this.takentime = takentime;
    }
}
