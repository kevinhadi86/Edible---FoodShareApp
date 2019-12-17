/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import edible.simple.model.dataEnum.StatusEnum;

/**
 * @author Kevin Hadinata
 * @version $Id: Transaction.java, v 0.1 2019‐09‐22 19:44 Kevin Hadinata Exp $$
 */
@Entity
@Table(name = "transactions")
public class Transaction extends DataAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;
    @NotNull
    private Float quantity;
    @NotNull
    private StatusEnum status;
    @Column(name = "pickup_time")
    private Date pickupTime;

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

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }
}
