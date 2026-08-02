package com.cs360.weighttracker.utils;

import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cs360.weighttracker.R;

public final class PasswordVisibilityToggler {

    /**
     * Update an edittext's password visibility and its triggering button's icon.
     *
     * @param passwordEditText The EditText text to toggle based on password status.
     * @param visibilityButton The ImageButton that toggle the visibility.
     * @implNote Uses the passed-in edittext's internal state to toggle visibility.
     */
    public static void togglePasswordState(EditText passwordEditText, ImageButton visibilityButton) {
        // Get the edittext's current visibility
        // Visible if edit text is normal text field
        boolean isVisible = passwordEditText.getInputType() == InputType.TYPE_CLASS_TEXT;

        // If currently visibly toggle to password text
        if (isVisible) {
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            visibilityButton.setImageResource(R.drawable.visibility_on);
        } else { // Otherwise toggle to normal text
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            visibilityButton.setImageResource(R.drawable.visibility_off);
        }
    }
}
