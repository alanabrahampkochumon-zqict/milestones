package com.cs360.weighttracker.validators;

public class WeightValidator {
    public static final float MIN_WEIGHT = 1.0f;
    public static final float MAX_WEIGHT = 700.0f;

    /**
     * Validates whether given weight is in bounds of MIN_WEIGHT and MAX_WEIGHT.
     *
     * @param weight The weight to validate.
     * @return Boolean indicating whether the weight is valid.
     */
    public static boolean validate(float weight) {
        return weight >= MIN_WEIGHT && weight <= MAX_WEIGHT;
    }
}
