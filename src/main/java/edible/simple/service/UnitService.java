/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.service;

import edible.simple.model.Unit;
import edible.simple.model.dataEnum.UnitName;

/**
 * @author Kevin Hadinata
 * @version $Id: UnitService.java, v 0.1 2019‐10‐03 11:31 Kevin Hadinata Exp $$
 */
public interface UnitService {

    public Unit getUnitByName(UnitName name);

}