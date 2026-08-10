package com.cs360.weighttracker.validators;

public class UsernameValidator {

    /**
     * Checks whether a username conforms to the required pattern.
     * A username is valid only if it doesn't contain any spaces.
     *
     * @param username The username to validate.
     * @return Boolean indicating whether the username is valid.
     */
    public static boolean validate(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return !username.contains(" ");
    }
}
