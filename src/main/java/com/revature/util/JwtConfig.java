package com.revature.util;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

@Component
public class JwtConfig {

    private String secretKey = "pineapple";
    private final SignatureAlgorithm SIG_ALG = SignatureAlgorithm.HS256;
    private final Key SIGNING_KEY;

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
