package com.cs360.weighttracker.database.status;

/**
 * Indicates different registration status and errors from repository layer.
 * NO_STATUS is a placeholder that must only be used for defining initial/undefined states.
 */
public enum RegisterStatus {
    USER_EXISTS, SUCCESS, UNKNOWN_FAILURE, NO_STATUS
}
