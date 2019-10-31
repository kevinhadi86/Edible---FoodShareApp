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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Kevin Hadinata
 * @version $Id: LocationServiceImpl.java, v 0.1 2019‐09‐18 17:03 Kevin Hadinata Exp $$
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public Location getLocationByCity(String city) {
        return locationRepository.getLocationByCity(city);
    }

    @Override
    public Set<String> getAllProvince() {

        Set<String> provinces = new HashSet<>();

        List<Location> locations = locationRepository.findAll();

        for (Location location: locations) {
            provinces.add(location.getProvince());
        }

        return provinces;
    }

    @Override
    public Set<String> getCityByProvince(String province) {

        Set<String> cities = new HashSet<>();

        Set<Location> locations = locationRepository.findAllByProvince(province);

        for (Location location: locations) {
            cities.add(location.getCity());
        }

        return cities;
    }

}
