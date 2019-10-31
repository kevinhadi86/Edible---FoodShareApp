/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author Kevin Hadinata
 * @version $Id: Location.java, v 0.1 2019‐09‐18 15:53 Kevin Hadinata Exp $$
 */
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String province;
    private String city;

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
