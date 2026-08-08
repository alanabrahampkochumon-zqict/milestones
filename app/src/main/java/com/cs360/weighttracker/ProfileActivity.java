package com.cs360.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.models.DailyWeight;
import com.cs360.weighttracker.models.GoalType;
import com.cs360.weighttracker.models.GoalWeight;
import com.cs360.weighttracker.models.User;
import com.cs360.weighttracker.utils.PasswordVisibilityToggler;

public class ProfileActivity extends AppCompatActivity {

    private TextView weightProgressTextView;
    private ProgressBar weightChangeProgressBar;

    private Switch notificationSwitch;
    private Button updateProfileButton, logoutButton;
    private ImageButton navigateBackButton;

    MilestoneRepository repository;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Repository is set to the application lifetime.
        repository = MilestoneRepository.getInstance(this.getApplicationContext());
        currentUser = repository.getCurrentUser();

        setupUI();
        setupEvents();
    }


    /**
     * Query and attach each view instance from XML layout.
     */
    private void setupUI() {
        weightChangeProgressBar = findViewById(R.id.pbProfileWeightChange);
        weightProgressTextView = findViewById(R.id.tvProfileWeightChange);
        navigateBackButton = findViewById(R.id.btnProfileNavigateBack);
        logoutButton = findViewById(R.id.btnProfileLogout);
        updateProfileButton = findViewById(R.id.btnProfileUpdateProfile);
        notificationSwitch = findViewById(R.id.switchProfileNotificationSetting);

        // Setup UI Text
        TextView fullNameTextView = findViewById(R.id.tvProfileFullName);
        // If the user has no full name, then use their username(This is highly unlikely).
        String fullName = currentUser.getFullName();
        fullName = !fullName.isEmpty() ? fullName : currentUser.getUserName();
        fullNameTextView.setText(fullName);

        // Update progress
        updateProgress();
    }


    private void updateProgress() {
        GoalWeight currentGoal = repository.getUserGoalWeight();
        if (currentGoal.getGoalType() == GoalType.WEIGHT_LOSS) {
            float currentWeight = 100.0f; // TODO: Update
            // Get current weight returns the user's initial weight
            // and we compare it the user's logged weight to get the progress
            float weightLost = currentGoal.getCurrentWeight() - currentWeight;
            float goalTotal = currentGoal.getCurrentWeight() - currentGoal.getGoalWeight();
            weightProgressTextView.setText(this.getString(R.string.weight_loss_progress, weightLost, goalTotal));
            weightChangeProgressBar.setProgress(Math.min(0, (int) (weightLost / goalTotal * 100)));
        } else {

            float currentWeight = 50.0f; // TODO: Update
            // For weight gain the current weight will be greater than the initial weight
            // else the progress will show negative values.
            float weightGained = currentWeight - currentGoal.getCurrentWeight();
            float goalTotal = currentGoal.getGoalWeight() - currentGoal.getCurrentWeight();
            weightProgressTextView.setText(this.getString(R.string.weight_gain_progress, weightGained, goalTotal));
            weightChangeProgressBar.setProgress(Math.min(0, (int) (weightGained / goalTotal * 100)));
        }
    }


    /**
     * Sets up the event listeners necessary for all the views.
     */
    private void setupEvents() {
        logoutButton.setOnClickListener(view -> {
            repository.logoutUser();
            navigateToLogin();
        });
        // Navigate back by popping out the current activity
        navigateBackButton.setOnClickListener(view -> {
            finish();
        });
        // TODO: Update progress bar
        // TODO: Update progress text
        // TODO: Add notification permission
        // TODO: Navigation to edit goal screen
    }


    /**
     * Handles navigation to login after logout.
     */
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}