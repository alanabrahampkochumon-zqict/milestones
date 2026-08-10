package com.cs360.weighttracker.validators;

public final class PhoneNumberValidator {

    /**
     * Validates a phone number as not empty and having less than 20 character.
     *
     * @param phoneNumber The phone number to validate.
     * @return A boolean indicating whether the phone number is valid.
     */
    public static boolean validate(String phoneNumber) {
        return !phoneNumber.trim().isEmpty() && phoneNumber.length() <= 20;
    }
}
