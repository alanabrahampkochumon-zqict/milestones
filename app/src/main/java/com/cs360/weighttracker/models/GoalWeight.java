package com.cs360.weighttracker.models;


/**
 * An interface to keep track of user's goal and target weight.
 */
public class GoalWeight {

    private float currentWeight, goalWeight;
    private long id;

    /**
     * Constructs a goal weight object with an id, current weight, and goal weight.
     *
     * @param id            The unique identifier of the goal weight.
     * @param currentWeight The current weight of the user.
     * @param goalWeight    The user's goal weight.
     */
    public GoalWeight(long id, float currentWeight, float goalWeight) {
        this.currentWeight = currentWeight;
        this.goalWeight = goalWeight;
        this.id = id;
    }


    /**
     * Constructs a goal weight object with the user's current weight, and goal weight.
     *
     * @param currentWeight The current weight of the user.
     * @param goalWeight    The user's goal weight.
     */
    public GoalWeight(float currentWeight, float goalWeight) {
        this.currentWeight = currentWeight;
        this.goalWeight = goalWeight;
        this.id = -1;
    }

    /// ////////////////////////
    ///       GETTERS      ///
    /// ///////////////////////

    public float getCurrentWeight() {
        return currentWeight;
    }

    public float getGoalWeight() {
        return goalWeight;
    }

    public long getId() {
        return id;
    }

    /// ////////////////////////
    ///       SETTERS      ///
    /// ///////////////////////

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public void setGoalWeight(float goalWeight) {
        this.goalWeight = goalWeight;
    }

    /**
     * Returns user goals given the current goal and target weights.
     */
    public GoalType getGoalType() {
        if (goalWeight > currentWeight) {
            return GoalType.WEIGHT_GAIN;
        } else if (goalWeight < currentWeight) {
            return GoalType.WEIGHT_LOSS;
        } else {
            return GoalType.WEIGHT_MAINTAIN;
        }
    }
}
