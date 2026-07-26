package com.cs360.weighttracker.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Model representing a user.
 *
 * @apiNote Password is stored as a hash and is immutable after creation.
 */
public class User {

    private long userId;
    private String username;
    private String hashedPassword;
    private String fullName;


    /**
     * Constructs user with a username, password, a user id, and a fullName.
     *
     * @param username The user's username.
     * @param password The user's password in hashed form.
     * @param userId   The user's unique identifier.
     * @param fullName The user's full name.
     * @apiNote Passwords must not be stored in plain text.
     * Use helper provided in `PasswordHasher` to hash the password for storage.
     */
    public User(long userId, String username, String password, String fullName) {
        this.username = username;
        this.hashedPassword = password;
        this.userId = userId;
        this.fullName = fullName;
    }

    /**
     * Constructs a user with a username and password.
     *
     * @param username The user's username.
     * @param password The user's password in hashed form.
     */
    public User(String username, String password) {
        this.username = username;
        this.hashedPassword = password;
        this.userId = -1;
        this.fullName = "";
    }


    /**
     * Compare if the given object and current instance are equal
     *
     * @param obj The object to compare current instance against.
     * @return True if all the properties of current object match the given object.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        // If object is not an instance of user return false
        if (!(obj instanceof User)) return false;

        // If they are equal then compare their username and password
        User user = (User) obj;
        return this.username.equals(user.username) && this.hashedPassword.equals(user.hashedPassword);
    }

    /**
     * Returns a string representation of current object.
     *
     * @return A string representation of the current user.
     * @apiNote Password is omitted.
     */
    @NonNull
    @Override
    public String toString() {
        return "Username = " + username;
    }


    /// ////////////////////////
    ///       GETTERS      ///
    /// ///////////////////////

    public String getUserName() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }


    /// ////////////////////////
    ///       SETTERS      ///
    /// ///////////////////////

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(long id) {
        this.userId = id;
    }
}
