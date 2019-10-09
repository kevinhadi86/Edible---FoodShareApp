/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.payload.offer;

/**
 * @author Kevin Hadinata
 * @version $Id: UpdateOfferRequest.java, v 0.1 2019‐09‐21 18:30 Kevin Hadinata Exp $$
 */
public class UpdateOfferRequest extends AddNewOfferRequest{

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
