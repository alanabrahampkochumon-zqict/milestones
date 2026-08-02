package com.cs360.weighttracker.utils;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeightFormatter {

    /**
     * Formats weight with 2 decimal precision and unit(uses KG).
     *
     * @param weight The weight to format.
     * @return A string representing the formatted weight.
     */
    @SuppressLint("DefaultLocale")
    public static String format(float weight) {
        String unit = " KG";
        DecimalFormat df = new DecimalFormat("00.00");
        return df.format(weight) + unit; // "05.35 KG"
    }
}
