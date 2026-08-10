package com.cs360.weighttracker.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    /**
     * Hashes a password using SHA-256 from plain text.
     *
     * @param plainText The plain text string to hash.
     * @return The hashed string.
     */
    public static String hash(String plainText) {
        StringBuilder hashString = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Decode the string to hashed byte array
            byte[] bytes = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));

            // Iterate through each byte, and convert it to a hex string
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hashString.append("0");
                hashString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            // Log not used here to keep the module structurally independent of android
            System.err.println("Invalid algorithm selected for hashing.");
        }

        return hashString.toString();
    }
}
