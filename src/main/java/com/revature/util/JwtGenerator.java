package com.revature.util;

import com.revature.dtos.Principal;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * The class that will use the JwtConfig to generate JWT tokens
 */
@Component
public class JwtGenerator {


    /**
     * The class that configures the JWT generation
     */
    private JwtConfig config;

    /**
     * JwtGenerator Constructor
     * @param config JwtConfig class to use
     */
    @Autowired
    public JwtGenerator(JwtConfig config) {
        this.config = config;
    }

    /**
     * Generates a Jwt based on the Principal provided
     * @param subject the Principal for a user that has logged in
     * @return
     */
    public String generateJwt (Principal subject) {

        // Current time. . .
        long now = System.currentTimeMillis();

        // Build the JWT. . .
        JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(subject.getId()))
                .setSubject(subject.getUsername())
                .claim("role", subject.getRole())
                .setIssuer("revature")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + (60 * 60 * 1000))) // Expires in an hour
                .signWith(config.getSignatureAlgorithm(), config.getSigningKey());

        return builder.compact();
    }
}
