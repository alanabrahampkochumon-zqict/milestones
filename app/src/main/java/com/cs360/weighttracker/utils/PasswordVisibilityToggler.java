package com.cs360.weighttracker.utils;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
        // Check if the password is visible
        int variation = passwordEditText.getInputType() & InputType.TYPE_MASK_VARIATION;
        boolean isVisible = (variation == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        // Cursor position is reset when we toggle edit text type, so we need to save it
        // and restore after transformation
        int cursorPosition = passwordEditText.getSelectionStart();

        if (isVisible) {
            // It is currently visible, hide it
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            visibilityButton.setImageResource(R.drawable.visibility_on);
        } else {
            // It is currently hidden, show it
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            visibilityButton.setImageResource(R.drawable.visibility_off);
        }
        // Restore cursor position to the end
        passwordEditText.setSelection(cursorPosition);
    }
}
