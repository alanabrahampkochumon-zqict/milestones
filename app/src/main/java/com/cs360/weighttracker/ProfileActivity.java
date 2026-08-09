package com.cs360.weighttracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

    private ActivityResultLauncher<String> requestPermissionLauncher;

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
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        toggleSMSSetting();
                    } else {
                        // Revert the switch visually because they denied it
                        notificationSwitch.setChecked(false);

                        // If the user denied the permission and show rationale is false, it means they permanently blocked it.
                        // in which case we display the dialog for opening setting and manually switching the permission
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                            showSettingsRedirectDialog();
                        }
                    }
                });

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
        DailyWeight latestWeight = repository.getUserLatestLoggedWeight();

        // If the user has not logged weight use the initial weight as the current weight
        float currentWeight = latestWeight == null ? currentGoal.getCurrentWeight() : latestWeight.getUserWeight();

        if (currentGoal.getGoalType() == GoalType.WEIGHT_LOSS) {
            // Get current weight returns the user's initial weight
            // and we compare it the user's logged weight to get the progress
            float weightLost = currentGoal.getCurrentWeight() - currentWeight;
            float goalTotal = currentGoal.getCurrentWeight() - currentGoal.getGoalWeight();
            weightProgressTextView.setText(this.getString(R.string.weight_loss_progress, weightLost, goalTotal));
            int progress = Math.clamp((int) (weightLost / goalTotal * 100), 0, 100);
            weightChangeProgressBar.setProgress(progress);
        } else {
            // For weight gain the current weight will be greater than the initial weight
            // else the progress will show negative values.
            float weightGained = currentWeight - currentGoal.getCurrentWeight();
            float goalTotal = currentGoal.getGoalWeight() - currentGoal.getCurrentWeight();
            weightProgressTextView.setText(this.getString(R.string.weight_gain_progress, weightGained, goalTotal));

            int progress = Math.clamp((int) (weightGained / goalTotal * 100), 0, 100);
            weightChangeProgressBar.setProgress(progress);
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
        updateProfileButton.setOnClickListener(view -> {
            navigateToGoals();
        });

        notificationSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            // Only ask permission for toggling it off
            if (isChecked) {
                handleSmsPermission(); // Only ask if they are turning it ON
            } else {
                // User turned it OFF. Just update the setting to false.
                // repository.setSmsEnabled(false);
                toggleSMSSetting();
            }
        });

    }


    /**
     * Handles the SMS permission.
     */
    private void handleSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            toggleSMSSetting(); // We have permission, turn it on!
        } else if (shouldShowRequestPermissionRationale(permission)) {
            // The permission is denied once
            showRationaleDialog();
        } else {
            // Asking user for permission for the first time.
            requestPermissionLauncher.launch(permission);
        }
    }


    /**
     * Shows a dialog to enable permission to the user
     * if the system allow the permission rationale(permission popup) to be shown.
     */
    private void showRationaleDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.sms_permission_dialog_title)
                .setMessage(R.string.sms_permission_body)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    // Request the system popup again
                    requestPermissionLauncher.launch(Manifest.permission.SEND_SMS);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    // Toggle the switch to off since the user denied permission
                    notificationSwitch.setChecked(false);
                    // Implicitly done
                })
                .show();
    }


    /**
     * Create a dialog to be displayed when the user permanently denies the permission.
     */
    private void showSettingsRedirectDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_denied_permanently_title)
                .setMessage(R.string.enable_permission_manually)
                .setPositiveButton(R.string.go_to_settings, (dialog, which) -> {
                    // Take them to the settings screen
                    openAppSettings();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    // Toggle the switch to off since the user denied permission
                    notificationSwitch.setChecked(false);
                    // Dialog is implicitly closed
                })
                .show();
    }


    /**
     * Open the application settings so that the user can turn on the permission manually
     */
    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void toggleSMSSetting() {
        // TODO:
    }


    /**
     * Handles navigation to login after logout.
     */
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    /**
     * Handles navigation to goal activity.
     */
    private void navigateToGoals() {
        Intent intent = new Intent(this, GoalsActivity.class);
        startActivity(intent);
    }

}