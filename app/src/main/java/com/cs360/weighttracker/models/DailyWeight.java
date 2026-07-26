package com.cs360.weighttracker.models;

public class DailyWeight {

    private float userWeight;
    private long dateTime, id;

    public DailyWeight(float userWeight, long dateTime, long id) {
        this.userWeight = userWeight;
        this.dateTime = dateTime;
        this.id = id;
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
