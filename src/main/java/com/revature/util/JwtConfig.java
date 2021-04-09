package com.revature.util;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * A class that handles the configuration of Java Web Tokens
 */
@Component
public class JwtConfig {

    /**
     * Secret key for creating a {@code SIGNING_KEY}
     */
    private String secretKey = "pineapple";

    /**
     * The algorithm to use
     */
    private final SignatureAlgorithm SIG_ALG = SignatureAlgorithm.HS256;

    /**
     * The signing key for the JWT
     */
    private final Key SIGNING_KEY;

    // Convert secret key into bytes and then generate a {@code SIGNING_KEY}
    {
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        SIGNING_KEY = new SecretKeySpec(secretBytes, SIG_ALG.getJcaName());
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey (String secretKey) {
        this.secretKey = secretKey;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return SIG_ALG;
    }

    public Key getSigningKey() {
        return SIGNING_KEY;
    }
}
