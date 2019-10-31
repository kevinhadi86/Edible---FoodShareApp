/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.Set;

import edible.simple.model.Location;

/**
 * @author Kevin Hadinata
 * @version $Id: LocationService.java, v 0.1 2019‐09‐18 17:02 Kevin Hadinata Exp $$
 */
public interface LocationService {

    public Location getLocationByCity(String city);

    public Set<String> getAllProvince();

    public Set<String> getCityByProvince(String province);

}