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
    private Float unit;

    public Long getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(Long offer_id) {
        this.offer_id = offer_id;
    }

    public Float getUnit() {
        return unit;
    }

    public void setUnit(Float unit) {
        this.unit = unit;
    }
}
