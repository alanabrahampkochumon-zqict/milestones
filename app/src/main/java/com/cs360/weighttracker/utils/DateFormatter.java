package com.cs360.weighttracker.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    /**
     * Formats time in millis to `Month Day, Year` format.
     *
     * @param millis Time in milliseconds
     * @return A string representation of the target datetime.
     */
    public static String fromMillis(Long millis) {
        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return formatter.format(date);
    }
}
