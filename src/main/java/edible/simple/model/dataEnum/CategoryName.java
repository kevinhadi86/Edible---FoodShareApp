/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model.dataEnum;

/**
 * @author Kevin Hadinata
 * @version $Id: CategoryName.java, v 0.1 2019‐09‐17 18:40 Kevin Hadinata Exp $$
 */
public enum CategoryName {
                          SNACK("Snack"), SAYUR("Sayur dan Buah"), BUMBU("Bumbu Dapur"), MINUMAN("Minuman Ringan"), DAGING("Daging");

    private final String toString;

    private CategoryName(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }

    public static CategoryName getByCode(String code) {
        for (CategoryName item : values()) {
            if (item.toString().equals(code)) {
                return item;
            }
        }
        return null;
    }
}