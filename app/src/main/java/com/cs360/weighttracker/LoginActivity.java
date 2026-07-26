package com.cs360.weighttracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        setupUI();
        setupEvents();
    }

    private void setupUI() {
        usernameEditText = findViewById(R.id.etLoginUsername);
        passwordEditText = findViewById(R.id.etLoginPassword);
        loginButton = findViewById(R.id.btnLogin);
        registerButton = findViewById(R.id.btnCreateAccount);
    }


    private void setupEvents() {
        loginButton.setOnClickListener(view -> {
            Log.d("TEST", "Login button clicked!");
        });
        registerButton.setOnClickListener(view ->{
            Log.d("TEST", "Register button clicked!");
        });
    }

    /// Establish flow
    // Register
    // Login
    // Username validation
    // Password validation
    // setup ui

}