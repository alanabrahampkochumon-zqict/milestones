package com.cs360.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.database.status.RegisterStatus;
import com.cs360.weighttracker.utils.PasswordVisibilityToggler;
import com.cs360.weighttracker.validators.PasswordValidator;
import com.cs360.weighttracker.validators.UsernameValidator;

public class RegisterActivity extends AppCompatActivity {

    TextView pageTitleTextView, usernameErrorTextView, passwordErrorTextView, confirmPasswordErrorTextView;
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
        usernameErrorTextView = findViewById(R.id.tvAuthUsernameError);
        passwordErrorTextView = findViewById(R.id.tvAuthPasswordError);
        confirmPasswordErrorTextView = findViewById(R.id.tvAuthConfirmPasswordError);
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
        registerButton.setOnClickListener(view -> register());

        passwordVisibilityToggleButton.setOnClickListener(view -> {
            PasswordVisibilityToggler.togglePasswordState(passwordEditText, passwordVisibilityToggleButton);
        });
        confirmPasswordVisibilityToggleButton.setOnClickListener(view -> {
            PasswordVisibilityToggler.togglePasswordState(confirmPasswordEditText, confirmPasswordVisibilityToggleButton);
        });


        usernameEditText.addTextChangedListener(new ResetErrorStateTextWatcher());
        passwordEditText.addTextChangedListener(new ResetErrorStateTextWatcher());
        confirmPasswordEditText.addTextChangedListener(new ResetErrorStateTextWatcher());

    }


    private void register() {
        // Store the state locally in the function rather than querying it
        // per function call to ensure that the validated username and password
        // is what gets passed down to the login function.
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        boolean isLoginValid = validateLogin(username, password, confirmPassword);
        if (isLoginValid) {
            RegisterStatus status = repository.registerUser(username, password);

            switch (status) {
                case USER_EXISTS:
                    usernameErrorTextView.setText(getApplicationContext().getString(R.string.no_user_match));
                    usernameErrorTextView.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    // We are not navigating to goals activity
                    // since we assume that the user has goal set
                    // indicated by the login rather than a register.
                    navigateToGoal();
                    break;
                case UNKNOWN_FAILURE:
                    Toast.makeText(this, R.string.unknown_error_signup, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }

    private void navigateToGoal() {
        Intent intent = new Intent(this, GoalsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    private boolean validateLogin(String username, String password, String confirmPassword) {

        boolean isValidLogin = true;
        if (!UsernameValidator.validate(username)) {
            usernameErrorTextView.setText(getApplicationContext().getString(R.string.invalid_username));
            usernameErrorTextView.setVisibility(View.VISIBLE);
            isValidLogin = false;
        }

        if (!PasswordValidator.validate(password)) {
            passwordErrorTextView.setText(getApplicationContext().getString(R.string.invalid_password));
            passwordErrorTextView.setVisibility(View.VISIBLE);
            isValidLogin = false;
        }

        if (!PasswordValidator.validate(confirmPassword) || !password.equals(confirmPassword)) {
            confirmPasswordErrorTextView.setText(getApplicationContext().getString(R.string.mismatched_passwords));
            confirmPasswordErrorTextView.setVisibility(View.VISIBLE);
            isValidLogin = false;
        }

        return isValidLogin;
    }


    /**
     * Reset the error state of the text views.
     */
    private void resetErrorState() {
        usernameErrorTextView.setVisibility(View.GONE);
        passwordErrorTextView.setVisibility(View.GONE);
        confirmPasswordErrorTextView.setVisibility(View.GONE);
    }

}