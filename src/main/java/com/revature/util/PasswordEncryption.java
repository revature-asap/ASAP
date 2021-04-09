package com.revature.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Class use to encrypt user's password
 */
public class PasswordEncryption {

    /**
     * Returns an encrypted password that is hashed
     * @param str password to encrypt
     * @return the encrypted password
     */
    public static String encryptString(String str) {
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, str.toCharArray());
        return bcryptHashString;
    }

    /**
     * Verifies a password
     * @param pw regular password
     * @param encryptedPassword encrypted password
     * @return {@code true} if the passwords match, otherwise {@code false}
     */
    public static boolean verifyPassword(String pw, String encryptedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(pw.toCharArray(), encryptedPassword);
        return result.verified;
    }
}
