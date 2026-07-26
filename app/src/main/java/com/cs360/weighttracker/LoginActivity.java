package com.cs360.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import com.cs360.weighttracker.validators.PasswordValidator;
import com.cs360.weighttracker.validators.UsernameValidator;

public class LoginActivity extends AppCompatActivity {


    TextView pageTitleTextView, usernameErrorTextView, passwordErrorTextView;
    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        setupUI();
        setupEvents();
    }

    private void setupUI() {
        usernameEditText = findViewById(R.id.etAuthUsername);
        passwordEditText = findViewById(R.id.etAuthPassword);
        loginButton = findViewById(R.id.btnAuthPrimary);
        registerButton = findViewById(R.id.btnAuthSecondary);
        pageTitleTextView = findViewById(R.id.tvAuthTitle);
        usernameErrorTextView = findViewById(R.id.tvAuthUsernameError);
        passwordErrorTextView = findViewById(R.id.tvAuthPasswordError);

        // Setup UI Text
        // Since we are using the auth layout for both login and register
        // we need to adjust the text accordingly
        pageTitleTextView.setText(getApplicationContext().getString(R.string.login_title));
        loginButton.setText(getApplicationContext().getString(R.string.login));
        registerButton.setText(getApplicationContext().getString(R.string.create_account));
    }


    private void setupEvents() {
        loginButton.setOnClickListener(view -> {
            login();
        });
        registerButton.setOnClickListener(view -> {
            navigateToRegister();
        });

        // If the text changes after an edit, the error state must be reset
        passwordEditText.addTextChangedListener(new ResetErrorStateTextWatcher());
        usernameEditText.addTextChangedListener(new ResetErrorStateTextWatcher());
    }


    private void navigateToRegister() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void validateLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!UsernameValidator.validate(username)) {
            usernameErrorTextView.setText(getApplicationContext().getString(R.string.invalid_username));
            usernameErrorTextView.setVisibility(View.VISIBLE);
        }

        if (!PasswordValidator.validate(password)) {
            passwordErrorTextView.setText(getApplicationContext().getString(R.string.invalid_password));
            passwordErrorTextView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Reset the error state of the text views.
     */
    private void resetErrorState() {
        usernameErrorTextView.setVisibility(View.GONE);
        passwordErrorTextView.setVisibility(View.GONE);
    }

    private void login() {
        validateLogin();
    }

    /// Establish flow
    // Register
    // Login
    // Username validation
    // Password validation
    // setup ui
    class ResetErrorStateTextWatcher implements TextWatcher {

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
}