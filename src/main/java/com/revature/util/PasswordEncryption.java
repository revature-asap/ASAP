package com.revature.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * class use to encrypt user's password
 */
public class PasswordEncryption {

    /**
     * returns an encrypted password that is hashed
     * @param str password to encrypt
     * @return returns an encrypted password
     */
    public static String encryptString(String str) {
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, str.toCharArray());
        return bcryptHashString;
    }

    /**
     * verifies the password
     * @param pw regulat password
     * @param encryptedPassword encrypted password
     * @return returns a boolean if the password matches
     */
    public static boolean verifyPassword(String pw, String encryptedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(pw.toCharArray(), encryptedPassword);
        return result.verified;
    }
}
