package com.cs360.weighttracker.database;

import android.util.Log;

import com.cs360.weighttracker.database.status.LoginStatus;
import com.cs360.weighttracker.database.status.RegisterStatus;
import com.cs360.weighttracker.models.DailyWeight;
import com.cs360.weighttracker.models.User;
import com.cs360.weighttracker.utils.LogCategory;
import com.cs360.weighttracker.utils.PasswordHasher;

import java.util.Date;

public class MilestoneRepository {

    private MilestoneDatabase database;
    private MilestonePrefs sharedPref;

    public MilestoneRepository(MilestoneDatabase database, MilestonePrefs sharedPref) {
        this.database = database;
        this.sharedPref = sharedPref;
    }


    ///////////////////////////
    ///     USER AUTH       //
    /////////////////////////

    /**
     * Logs in the user with the given username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return LoginStatus indicating whether the login was successful or not.
     */
    LoginStatus loginUser(String username, String password) {
        String hashedPassword = PasswordHasher.hash(password);
        User user = new User(username, password);
        try {
            // Try to validate the user to know if they exist in the database.
            User loggedInUser = database.validateUser(user);
            if (loggedInUser != null && loggedInUser.getUserId() != -1) {
                // Store the user's current session
                sharedPref.setCurrentUserId(loggedInUser.getUserId());
                return LoginStatus.SUCCESS;
            } else {
                // Try to check whether the user exists but has an incorrect password
                loggedInUser = database.getUser(user.getUserName());
                if (loggedInUser != null) { // Indicates user exists but password is incorrect
                    return LoginStatus.PASSWORD_ERROR;
                } else { // Else the user doesn't exist.
                    return LoginStatus.USERNAME_ERROR;
                }
            }
        } catch (Exception e) {
            Log.e(LogCategory.REPOSITORY, "There was error logging the user in!\n" + e.getMessage());
        }
        return LoginStatus.UNKNOWN_FAILURE;
    }

    RegisterStatus registerUser(String username, String password) {
        String hashedPassword = PasswordHasher.hash(password);
        try {
            // Check whether the user exists in the database.
            User existingUser = database.getUser(username);
            if (existingUser != null) {
                return RegisterStatus.USER_EXISTS;
            }
            // Register the user and save the user session to shared pref
            User newUser = new User(username, hashedPassword);
            long userId = database.insertUser(newUser);

            // If the insert was a success login in the user
            if (userId != -1) {
                sharedPref.setCurrentUserId(userId);
                return RegisterStatus.SUCCESS;
            } else { // Return error status indicating login error
                return RegisterStatus.UNKNOWN_FAILURE;
            }
        } catch (Exception e) {
            Log.e(LogCategory.REPOSITORY, "There was error registering the user!\n" + e.getMessage());
        }

        return RegisterStatus.UNKNOWN_FAILURE;
    }

    /**
     * Logs out the current user.
     */
    void logoutUser() {
        sharedPref.deleteCurrentUserId();
    }


    /// ////////////////////////
    ///     USER WEIGHT    ///
    /// ///////////////////////
//    boolean logDailyWeight(float weight) {
//        try {
//            DailyWeight dailyWeight = new DailyWeight(weight, Date.);
//        } catch (Exception e) {
//            Log.e(LogCategory.REPOSITORY, "There was error logging the user weight!\n" + e.getMessage());
//        }
//        return false;
//    }

}
