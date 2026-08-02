package com.cs360.weighttracker.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * TextWatcher for clearing error states when the attached text field's value changes.
 * TODO: Remove
 */
public class SimpleTextWatcher implements TextWatcher {

    ErrorResetCallback callback;

    /**
     * Construct a TextWatcher with callback that get triggered whenever the target's input changes.
     *
     * @param callback The callback that gets triggered when TextWatcher's onTextChanged is called.
     */
    public SimpleTextWatcher(ErrorResetCallback callback) {
        this.callback = callback;
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        callback.onCallback();
    }
}