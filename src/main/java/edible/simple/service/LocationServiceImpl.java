/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edible.simple.model.Location;
import edible.simple.model.User;
import edible.simple.repository.LocationRepository;

/**
 * @author Kevin Hadinata
 * @version $Id: LocationServiceImpl.java, v 0.1 2019‐09‐18 17:03 Kevin Hadinata Exp $$
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public boolean saveLocation(Location location) {
        if(!locationRepository.save(location).equals(null)){
            return true;
        }
        return false;
    }

    @Override
    public Location getLocationByUser(User user) {
        return locationRepository.getByUser(user);
    }
}
