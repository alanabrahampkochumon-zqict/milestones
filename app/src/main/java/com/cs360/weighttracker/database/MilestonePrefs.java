package com.cs360.weighttracker.database;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class MilestonePrefs {

    private final String PREF_NAME = "milestone_prefs";
    private final String CURRENT_SESSION_USER_ID = "user-id";
    private final String SMS_SETTING_KEY = "sms-settings";

    private final SharedPreferences sharedPreferences;

    public MilestonePrefs(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Set the user's SMS setting.
     *
     * @param status The flag indicating whether the user has SMS option turned on or off.
     */
    public void setSMSSetting(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SMS_SETTING_KEY, status);
        editor.apply();
    }


    /**
     * Get the user's SMS setting.
     *
     * @return A boolean indicating whether the user has the SMS option turned on or off.
     * @apiNote If the preference is not found, then false is returned by default.
     */
    public boolean getSMSSetting() {
        return sharedPreferences.getBoolean(SMS_SETTING_KEY, false);
    }


    /**
     * Set the current user's id into the shared preferences.
     *
     * @param id The user id to insert.
     */
    public void setCurrentUserId(long id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(CURRENT_SESSION_USER_ID, id);
        editor.apply();
    }

    /**
     * Get the current logged-in user's id.
     *
     * @return User id if there is a logged-in user, else -1.
     */
    public long getCurrentUserId() {
        return sharedPreferences.getLong(CURRENT_SESSION_USER_ID, -1);
    }

    /**
     * Delete the current user id from the shared preference.
     */
    public void deleteCurrentUserId() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CURRENT_SESSION_USER_ID);
        editor.apply();
    }
}
