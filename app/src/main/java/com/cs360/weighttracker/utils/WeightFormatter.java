package com.cs360.weighttracker.utils;

import android.annotation.SuppressLint;

public class WeightFormatter {

    /**
     * Formats weight with 2 decimal precision and unit(uses KG).
     *
     * @param weight The weight to format.
     * @return A string representing the formatted weight.
     */
    @SuppressLint("DefaultLocale")
    public static String format(float weight) {
        String formatString = "%02.02f KG";
        return String.format(formatString, weight); // "05.35 KG"
    }
}
