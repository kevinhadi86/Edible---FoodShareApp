/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.security;

import java.lang.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * @author Kevin Hadinata
 * @version $Id: CurrentUser.java, v 0.1 2019‐09‐11 19:19 Kevin Hadinata Exp $$
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}