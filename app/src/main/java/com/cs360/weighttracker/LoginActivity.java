package com.cs360.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.database.status.LoginStatus;
import com.cs360.weighttracker.utils.PasswordVisibilityToggler;
import com.cs360.weighttracker.validators.PasswordValidator;
import com.cs360.weighttracker.validators.UsernameValidator;

public class LoginActivity extends AppCompatActivity {


    TextView pageTitleTextView, usernameErrorTextView, passwordErrorTextView;
    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;

    MilestoneRepository repository;
    ImageButton passwordVisibilityToggleButton;

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
        loginButton = findViewById(R.id.btnAuthPrimary);
        registerButton = findViewById(R.id.btnAuthSecondary);
        pageTitleTextView = findViewById(R.id.tvAuthTitle);
        usernameErrorTextView = findViewById(R.id.tvAuthUsernameError);
        passwordErrorTextView = findViewById(R.id.tvAuthPasswordError);
        passwordVisibilityToggleButton = findViewById(R.id.btnAuthPasswordVisibility);

        // Setup UI Text
        // Since we are using the auth layout for both login and register
        // we need to adjust the text accordingly
        pageTitleTextView.setText(getApplicationContext().getString(R.string.login_title));
        loginButton.setText(getApplicationContext().getString(R.string.login));
        registerButton.setText(getApplicationContext().getString(R.string.create_account));
    }


    /**
     * Sets up the event listeners necessary for all the views.
     */
    private void setupEvents() {
        loginButton.setOnClickListener(view -> {
            login();
        });
        registerButton.setOnClickListener(view -> {
            navigateToRegister();
        });
        passwordVisibilityToggleButton.setOnClickListener(view -> {
            PasswordVisibilityToggler.togglePasswordState(passwordEditText, passwordVisibilityToggleButton);
        });

        // If the text changes after an edit, the error state must be reset
        passwordEditText.addTextChangedListener(new ResetErrorStateTextWatcher());
        usernameEditText.addTextChangedListener(new ResetErrorStateTextWatcher());
    }


    /**
     * Navigate to the register page.
     *
     * @implNote This will not clear the "navigation stack" i.e,
     * the register activity will be placed on top of the login screen.
     */
    private void navigateToRegister() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }


    /**
     * Performs validation on the login credentials.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A boolean indicating whether the validation was successful.
     */
    private boolean validateLogin(String username, String password) {

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

        return isValidLogin;
    }


    private void login() {
        // Store the state locally in the function rather than querying it
        // per function call to ensure that the validated username and password
        // is what gets passed down to the login function.
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean isLoginValid = validateLogin(username, password);
        if (isLoginValid) {
            LoginStatus status = repository.loginUser(username, password);

            switch (status) {
                case USERNAME_ERROR:
                    usernameErrorTextView.setText(getApplicationContext().getString(R.string.no_user_match));
                    usernameErrorTextView.setVisibility(View.VISIBLE);
                    break;
                case PASSWORD_ERROR:
                    passwordErrorTextView.setText(getApplicationContext().getString(R.string.incorrect_password));
                    passwordErrorTextView.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    // We are not navigating to goals activity
                    // since we assume that the user has goal set
                    // indicated by the login rather than a register.
                    navigateToHome();
                    break;
                case UNKNOWN_FAILURE:
                    Toast.makeText(this, R.string.unknown_error_signin, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * Navigates the user to the home activity.
     *
     * @implNote The "navigation backstack" is cleared.
     */
    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
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

    // TODO: Unhide password

    /**
     * Reset the error state of the text views.
     */
    private void resetErrorState() {
        usernameErrorTextView.setVisibility(View.GONE);
        passwordErrorTextView.setVisibility(View.GONE);
    }
}