package com.cs360.weighttracker.utils;

import androidx.annotation.StringRes;

import com.cs360.weighttracker.R;

import java.util.Calendar;

public final class TimeUtils {

    /**
     * Determines the appropriate greeting based on the device's current local time.
     *
     * @return The String resource ID for the time-based greeting.
     */
    @StringRes
    public static int getGreetingResId() {
        // HOUR_OF_DAY returns the hour in 24-hour format (0-23)
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            return R.string.greeting_morning;   // 12:00 AM to 11:59 AM
        } else if (hour < 17) {
            return R.string.greeting_afternoon; // 12:00 PM to 4:59 PM
        } else {
            return R.string.greeting_evening;   // 5:00 PM to 11:59 PM
        }
    }
}
