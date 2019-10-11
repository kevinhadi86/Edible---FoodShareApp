/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

import edible.simple.model.dataEnum.UnitName;

/**
 * @author Kevin Hadinata
 * @version $Id: Unit.java, v 0.1 2019‐10‐02 18:45 Kevin Hadinata Exp $$
 */
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long     id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "unit_name")
    private UnitName unitName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnitName getUnitName() {
        return unitName;
    }

    public void setUnitName(UnitName unitName) {
        this.unitName = unitName;
    }
}
