package com.cs360.weighttracker;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

public class GoalsActivity extends AppCompatActivity {


    private EditText fullNameEditText, currentWeightEditText, goalWeightEditText;
    private Button getStartedButton;
    private TextView fullNameErrorTextview, currentWeightErrorTextView, goalWeightErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_goals);

        setupUI();
        setupEvents();
    }


    /**
     * Query and attach each view instance from XML layout.
     */
    private void setupUI() {
        fullNameEditText = findViewById(R.id.etDetailsFullName);
        currentWeightEditText = findViewById(R.id.etDetailsCurrentWeight);
        goalWeightEditText = findViewById(R.id.etDetailsGoalWeight);

        fullNameErrorTextview = findViewById(R.id.tvDetailsFullNameError);
        currentWeightErrorTextView = findViewById(R.id.tvDetailsCurrentWeightError);
        goalWeightErrorTextView = findViewById(R.id.tvDetailsGoalWeightError);

        getStartedButton = findViewById(R.id.btnDetailsGetStarted);
    }


    /**
     * Sets up the event listeners necessary for all the views.
     */
    private void setupEvents() {
        getStartedButton.setOnClickListener(view -> {
            getStarted();
        });

        // If the text changes after an edit, the error state must be reset
        fullNameEditText.addTextChangedListener(new ResetErrorStateTextWatcher());
        currentWeightEditText.addTextChangedListener(new ResetErrorStateTextWatcher());
        goalWeightEditText.addTextChangedListener(new ResetErrorStateTextWatcher());
    }


    /**
     * Handles event propagation from Get Started Button.
     */
    private void getStarted() {
        //TODO: Validation
        //TODO: Navigation

    }


    /**
     * TextWatcher for clearing error states when username or password updates.
     */
    private class ResetErrorStateTextWatcher implements TextWatcher {

        @Override
        public void afterTextChanged(Editable editable) {
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            resetErrorState();
        }
    }

    /**
     * Reset the error state of the text views.
     */
    private void resetErrorState() {
        fullNameErrorTextview.setVisibility(View.GONE);
        currentWeightErrorTextView.setVisibility(View.GONE);
        goalWeightErrorTextView.setVisibility(View.GONE);
    }

}