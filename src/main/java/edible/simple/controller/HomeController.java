/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kevin Hadinata
 * @version $Id: HomeController.java, v 0.1 2019‐09‐13 12:10 Kevin Hadinata Exp $$
 */
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping("/home")
    public String openHome(){
        return "haha";
    }
}
