/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.security;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edible.simple.model.User;
import edible.simple.repository.UserRepository;
import io.jsonwebtoken.*;

/**
 * @author Kevin Hadinata
 * @version $Id: JwtTokenProvider.java, v 0.1 2019‐09‐11 18:37 Kevin Hadinata Exp $$
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String              jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long                jwtExpirationInMs;

    @Autowired
    UserRepository              userRepository;

    public String generateToken(User user) {

        Date now = new Date();
        Date expirtDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder().setSubject(Long.toString(user.getId()))
            .setIssuedAt(new Date()).setExpiration(expirtDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
