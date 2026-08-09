package com.cs360.weighttracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.models.User;
import com.cs360.weighttracker.utils.PasswordVisibilityToggler;

public class ProfileActivity extends AppCompatActivity {

    private TextView weightProgressTextView;
    private ProgressBar weightChangeProgressBar;

    private Switch notificationSwitch;
    private Button updateProfileButton, logoutButton;
    private ImageButton navigateBackButton;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    toggleSMSSetting();
                } else {
                    // Explain to the user that the feature is unavailable.
                    showRationaleDialog();
                }
            });

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


    private void handleSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;

        // Check if permission is already granted
        // If so send sms
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            toggleSMSSetting();
            return;
        }

        // Check if we should show an educational explanation (Rationale)
        ActivityResultLauncher<String> requestPermissionLauncher;
        if (shouldShowRequestPermissionRationale(permission)) {
            // Show an AlertDialog here explaining WHY you need SMS.
            // Inside that dialog's "OK" button, launch the standard popup:
//            requestPermissionLauncher.launch(permission);
            showSettingsRedirectDialog();
        } else {
            // This runs if it's the very first time, OR if they selected "Don't ask again"
            // Try launching the popup first.
            showRationaleDialog();
        }
    }


    // Call this when shouldShowRequestPermissionRationale() is TRUE
    private void showRationaleDialog() {
        new AlertDialog.Builder(this)
                .setTitle("SMS Permission Required")
                .setMessage("This app needs SMS permission to send your alerts. Please allow it on the next screen.")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Request the system popup again
                    requestPermissionLauncher.launch(Manifest.permission.SEND_SMS);
                })
                .setNegativeButton("Cancel", null) // Just closes the dialog
                .show();
    }

    // Call this inside the "else" block of your launcher if it keeps returning false
    private void showSettingsRedirectDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Permanently Denied")
                .setMessage("You have disabled SMS permissions. Please enable them manually in the app settings to use this feature.")
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    // Take them to the settings screen
                    openAppSettings();
                })
                .setNegativeButton("Cancel", null)
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

}