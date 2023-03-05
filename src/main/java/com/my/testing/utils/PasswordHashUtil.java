package com.my.testing.utils;

import com.my.testing.exceptions.IncorrectPasswordException;
import de.mkammerer.argon2.*;
import lombok.*;

/**
 * Encode and verify encoded passwords. Uses Argon2 library to encode
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordHashUtil {
    private static final Argon2 argon2 = Argon2Factory.create();
    private static final int ITERATIONS = 2;
    private static final int MEMORY = 15*1024;
    private static final int PARALLELISM = 1;

    /**
     * Encodes user's password
     * @param password to properly encode user not null value
     * @return encoded password
     */
    public static String encode(String password) {
        return password != null ? argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray()) : "";
    }

    /**
     * Verifies password
     * @param hash encoded password
     * @param password password value
     * @throws IncorrectPasswordException in case of vad verification
     */
    public static void verify(String hash, String password) throws IncorrectPasswordException {
        if (!argon2.verify(hash, password.toCharArray())) {
            throw new IncorrectPasswordException();
        }
    }
}
