/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;
    @NotNull
    private Float unit;
    @NotNull
    private StatusEnum status;
    private Date takentime;

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

    public Float getUnit() {
        return unit;
    }

    public void setUnit(Float unit) {
        this.unit = unit;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getTakentime() {
        return takentime;
    }

    public void setTakentime(Date takentime) {
        this.takentime = takentime;
    }
}
