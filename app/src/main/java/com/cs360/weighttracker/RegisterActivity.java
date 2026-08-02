package com.cs360.weighttracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.utils.PasswordVisibilityToggler;

public class RegisterActivity extends AppCompatActivity {

    TextView pageTitleTextView;
    EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    Button loginButton, registerButton;
    ImageButton passwordVisibilityToggleButton, confirmPasswordVisibilityToggleButton;

    MilestoneRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        // Attach to application context to ensure the repository share the application's lifetime
        // not getting recreated for every shared activity.
        repository = MilestoneRepository.getInstance(getApplicationContext());

        setupUI();
        setupEvents();
    }


    /**
     * Query and attach each view instance from XML layout.
     */
    private void setupUI() {
        usernameEditText = findViewById(R.id.etAuthUsername);
        passwordEditText = findViewById(R.id.etAuthPassword);
        confirmPasswordEditText = findViewById(R.id.etAuthConfirmPassword);
        // We have swapped the button labels to ensure that primary button leads to the primary action(create account)
        registerButton = findViewById(R.id.btnAuthPrimary);
        loginButton = findViewById(R.id.btnAuthSecondary);
        pageTitleTextView = findViewById(R.id.tvAuthTitle);
        passwordVisibilityToggleButton = findViewById(R.id.btnAuthPasswordVisibility);
        confirmPasswordVisibilityToggleButton = findViewById(R.id.btnAuthConfirmPasswordVisibility);

        // Since we are reusing the layout for login and register, we need to unhide the confirm password field
        // Since the layout is not further manipulated or queried, local variable is sufficient here
        LinearLayout confirmPasswordLayout = findViewById(R.id.layoutAuthConfirmPassword);
        confirmPasswordLayout.setVisibility(View.VISIBLE);

        // Setup UI Text
        // Since we are using the auth layout for both login and register
        // we need to adjust the text accordingly
        pageTitleTextView.setText(getApplicationContext().getString(R.string.register_title));
        registerButton.setText(getApplicationContext().getString(R.string.create_account));
        loginButton.setText(getApplicationContext().getString(R.string.login));
    }

    
    /**
     * Sets up the event listeners necessary for all the views.
     */
    private void setupEvents() {
        // Finish will pop off this activity from the backstack leaving only with the Login activity
        // from the navigation will take place
        loginButton.setOnClickListener(view -> finish());

        passwordVisibilityToggleButton.setOnClickListener(view -> {
            PasswordVisibilityToggler.togglePasswordState(passwordEditText, passwordVisibilityToggleButton);
        });
        confirmPasswordVisibilityToggleButton.setOnClickListener(view -> {
            PasswordVisibilityToggler.togglePasswordState(confirmPasswordEditText, confirmPasswordVisibilityToggleButton);
        });

        registerButton.setOnClickListener(view -> {
            Log.d("TEST", "registering...");
        });
    }

}