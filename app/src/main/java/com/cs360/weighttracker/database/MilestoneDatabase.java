package com.cs360.weighttracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cs360.weighttracker.models.User;
import com.cs360.weighttracker.utils.LogCategory;

public class MilestoneDatabase extends SQLiteOpenHelper {

    public MilestoneDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final String DATABASE_NAME = "milestones.db";
    private static final int VERSION = 1;

    private static final class UserTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";

        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD_HASH = "password_hash";
    }

    private static final class DailyWeightTable {
        private static final String TABLE = "daily_weight";
        private static final String COL_ID = "_id";

        private static final String COL_WEIGHT = "weight";
        private static final String COL_DATE = "date";

    }


    private static final class GoalWeightTable {
        private static final String TABLE = "goal_weight";
        private static final String COL_ID = "_id";

        private static final String COL_CUR_WEIGHT = "current_weight";
        private static final String COL_GOAL_WEIGHT = "goal_weight";

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create all the databases
        String userTableQuery = "CREATE TABLE " +
                UserTable.TABLE + " (" +
                UserTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COL_USERNAME + " TEXT, " +
                UserTable.COL_PASSWORD_HASH + " TEXT)";

        String dailyWeightTableQuery = "CREATE TABLE " +
                DailyWeightTable.TABLE + " (" +
                DailyWeightTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DailyWeightTable.COL_WEIGHT + " REAL, " +
                DailyWeightTable.COL_DATE + " INTEGER)";

        String goalWeightTableQuery = "CREATE TABLE " +
                GoalWeightTable.TABLE + " (" +
                GoalWeightTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GoalWeightTable.COL_GOAL_WEIGHT + " REAL, " +
                GoalWeightTable.COL_CUR_WEIGHT + " REAL)";

        sqLiteDatabase.execSQL(userTableQuery);
        sqLiteDatabase.execSQL(dailyWeightTableQuery);
        sqLiteDatabase.execSQL(goalWeightTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop all the databases
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DailyWeightTable.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GoalWeightTable.TABLE);

        // Create the database tables
        onCreate(sqLiteDatabase);
    }

    // TODO: CRUD user
    // TODO: CRUD Daily Weight
    // TODO: CRUD Goal Weight
    // TODO: Add foreign key for connecting user with goal and daily weights

    //////////////////////////////
    ///                         //
    ///        USER CRUD        //
    ///                         //
    //////////////////////////////

    /**
     * Inserts a user into the database.
     *
     * @param user The user to be inserted.
     * @return The id of the inserted user.
     */
    // TODO: Name conflicts
    public long insertUser(@NonNull User user) {
        SQLiteDatabase db = getReadableDatabase();

        // Create values for insertion
        ContentValues values = new ContentValues();
        values.put(UserTable.COL_USERNAME, user.getUserName());
        values.put(UserTable.COL_PASSWORD_HASH, user.getHashedPassword());

        // Insert the value into database
        return db.insert(UserTable.TABLE, null, values);
    }


    /**
     * Get a user from the database with the given username.
     *
     * @param username The username to match against.
     * @return A user object, if the user exist and null otherwise.
     */
    public User getUser(@NonNull String username) {
        SQLiteDatabase db = getReadableDatabase();

        // Execute the query
        String query = "SELECT * FROM " + UserTable.TABLE + " WHERE " + UserTable.COL_USERNAME + " = ? LIMIT 1";
        try (Cursor cursor = db.rawQuery(query, new String[]{username})) {
            if (cursor.moveToFirst()) {
//                long id = cursor.getLong(0);
                // Try to return the instance if it exists
                String password = cursor.getString(2);
                return new User(username, password);
            }
        } catch (Exception e) {
            Log.e(LogCategory.DATABASE, "There was an error getting the user.\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Validates whether the given user credentials are valid.
     *
     * @param user The user to validate.
     * @return True if the provided credentials are valid, and false otherwise.
     * @apiNote The method doesn't return whether the user exists, only if the credentials are valid.
     * To know if a user exists, user `getUser` with username.
     */
    public boolean validateUser(@NonNull User user) {
        SQLiteDatabase db = getReadableDatabase();

        // Execute the query
        String query = "SELECT * FROM " + UserTable.TABLE + " WHERE " + UserTable.COL_USERNAME + " = ? AND " + UserTable.COL_PASSWORD_HASH + " = ?";
        try (Cursor cursor = db.rawQuery(query, new String[]{user.getUserName(), user.getHashedPassword()})) {
            if (cursor.moveToFirst()) {
                return true;
            }
        } catch (Exception e) {
            Log.e(LogCategory.DATABASE, "There was an validating the user.\n" + e.getMessage());
        }
        return false;
    }

    public void updateUser(@NonNull User user) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserTable.COL_USERNAME, user.getUserName());
        values.put(UserTable.COL_PASSWORD_HASH, user.getHashedPassword());

        // TODO: Work from here
//        int rowsUpdated = db.update(UserTable.TABLE, values, "")
    }
}
