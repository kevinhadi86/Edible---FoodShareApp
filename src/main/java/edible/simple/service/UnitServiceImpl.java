/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edible.simple.model.Unit;
import edible.simple.model.dataEnum.UnitName;
import edible.simple.repository.UnitRepository;

/**
 * @author Kevin Hadinata
 * @version $Id: UnitServiceImpl.java, v 0.1 2019‐10‐03 11:32 Kevin Hadinata Exp $$
 */
@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Override
    public Unit getUnitByName(UnitName name) {

        Optional<Unit> unit = unitRepository.findByName(name);

        if(unit.isPresent()){
            return unit.get();
        }

        return null;
    }
}
