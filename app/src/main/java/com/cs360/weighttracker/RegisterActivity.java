package com.cs360.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    TextView pageTitleTextView;
    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;
    LinearLayout confirmPasswordLayout;

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
        // We have swapped the button labels to ensure that primary button leads to the primary action(create account)
        registerButton = findViewById(R.id.btnAuthPrimary);
        loginButton = findViewById(R.id.btnAuthSecondary);
        pageTitleTextView = findViewById(R.id.tvAuthTitle);
        // Since we are reusing the layout for login and register, we need to unhide the confirm password field
        confirmPasswordLayout = findViewById(R.id.layoutAuthConfirmPassword);

        // Setup UI Text
        // Since we are using the auth layout for both login and register
        // we need to adjust the text accordingly
        pageTitleTextView.setText(getApplicationContext().getString(R.string.register_title));
        registerButton.setText(getApplicationContext().getString(R.string.create_account));
        loginButton.setText(getApplicationContext().getString(R.string.login));
    }


    private void setupEvents() {
        // Finish will pop off this activity from the backstack leaving only with the Login activity
        // from the navigation will take place
        loginButton.setOnClickListener(view -> finish());
        registerButton.setOnClickListener(view -> {
            Log.d("TEST", "registering...");
        });
    }

}