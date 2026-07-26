package com.cs360.weighttracker.models;

public class DailyWeight {

    private float userWeight;
    private long dateTime;
    private final long id;

    /**
     * Constructs a Daily weight object with an id, user weight and datetime(in millis).
     *
     * @param id         The unique identifier of the daily weight object.
     * @param userWeight The user's weight.
     * @param dateTime   The datetime during which the weight was recorded.
     */
    public DailyWeight(long id, float userWeight, long dateTime) {
        this.userWeight = userWeight;
        this.dateTime = dateTime;
        this.id = id;
    }


    /**
     * Constructs a Daily weight object with a user weight and datetime(in millis).
     *
     * @param userWeight The user's weight.
     * @param dateTime   The datetime during which the weight was recorded.
     */
    public DailyWeight(float userWeight, long dateTime) {
        this.userWeight = userWeight;
        this.dateTime = dateTime;
        this.id = -1;
    }


    ///////////////////////////
    ///       GETTERS      ///
    //////////////////////////

    /**
     * Returns the user weight as a float.
     */
    public float getUserWeight() {
        return userWeight;
    }

    /**
     * Returns the datetime in milliseconds.
     */
    public long getDateTimeMillis() {
        return dateTime;
    }

    public long getId() {
        return id;
    }

    /// ////////////////////////
    ///       SETTERS      ///
    /// ///////////////////////

    public void setUserWeight(float userWeight) {
        this.userWeight = userWeight;
    }


    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

}
