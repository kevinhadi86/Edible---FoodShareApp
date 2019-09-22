/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import edible.simple.model.Location;
import edible.simple.model.User;

/**
 * @author Kevin Hadinata
 * @version $Id: LocationService.java, v 0.1 2019‐09‐18 17:02 Kevin Hadinata Exp $$
 */
public interface LocationService {

    public boolean saveLocation(Location location);

    public Location getLocationByUser(User user);
}