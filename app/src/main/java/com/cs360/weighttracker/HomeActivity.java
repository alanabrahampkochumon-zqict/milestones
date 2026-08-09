package com.cs360.weighttracker;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cs360.weighttracker.components.AddWeightDialog;
import com.cs360.weighttracker.components.DeleteWeightItemDialog;
import com.cs360.weighttracker.components.WeightItemAdapter;
import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.models.DailyWeight;
import com.cs360.weighttracker.models.GoalType;
import com.cs360.weighttracker.models.GoalWeight;
import com.cs360.weighttracker.models.User;

import java.util.List;

public class HomeActivity extends AppCompatActivity {


    TextView fullNameTextView;
    Button trackWeightButton;
    ImageView profileImage;
    RecyclerView progressHistoryRecyclerView;


    MilestoneRepository repository;
    User currentUser;

    WeightItemAdapter adapter;

    // TODO: Add weight progress on top of track weight

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Attach to application context to ensure the repository share the application's lifetime
        // not getting recreated for every shared activity.
        repository = MilestoneRepository.getInstance(getApplicationContext());
        currentUser = repository.getCurrentUser();

        setupUI();
        setupEvents();
    }


    /**
     * Query and attach each view instance from XML layout.
     */
    private void setupUI() {
        trackWeightButton = findViewById(R.id.btnHomeTrackWeight);
        fullNameTextView = findViewById(R.id.tvHomeFullName);
        progressHistoryRecyclerView = findViewById(R.id.rvHomeHistory);
        profileImage = findViewById(R.id.imgHomeProfile);

        // Setup UI Text
        String fullName = currentUser.getFullName();
        fullNameTextView.setText(fullName);

        setupRecyclerView();
    }


    private void setupRecyclerView() {
        List<DailyWeight> weightList = repository.getDailyWeights();
        adapter = new WeightItemAdapter(weightList, itemId -> {
            // For deleting the weight, to provide the user with a chance to rethink their
            // show a dialog with a confirmation. On confirmation, delete the weight.
            DeleteWeightItemDialog dialog = new DeleteWeightItemDialog(() -> {
                boolean weightDeleted = repository.deleteDailyWeight(itemId);
                if (weightDeleted) {
                    refreshHistory();
                    Toast.makeText(this, "Weight deleted successfully.", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show(getSupportFragmentManager(), Constants.DELETE_WEIGHT_ITEM_DIALOG);
        });
        progressHistoryRecyclerView.setAdapter(adapter);
    }


    /**
     * Refresh the weight progress data with new data from the repository.
     */
    private void refreshHistory() {
        List<DailyWeight> newData = repository.getDailyWeights();
        adapter.updateData(newData);
    }


    /**
     * Sets up the event listeners necessary for all the views.
     */
    private void setupEvents() {
        // Finish will pop off this activity from the backstack leaving only with the Login activity
        // from the navigation will take place
        trackWeightButton.setOnClickListener(view -> {
            AddWeightDialog dialog = new AddWeightDialog();
            dialog.show(getSupportFragmentManager(), Constants.ADD_NEW_WEIGHT_DIALOG);
        });

        // Add event listener for the dialog
        getSupportFragmentManager().setFragmentResultListener(Constants.ADD_NEW_WEIGHT_DIALOG, this, ((requestKey, result) -> {
            float weight = result.getFloat(Constants.NEW_WEIGHT_BUNDLE_KEY);

            if (repository.logDailyWeight(weight)) {
                refreshHistory();
                // Send a sms if the user surpassed or reached the goal weight and have the permission and phone number set
                if (hasReachGoalWeight(weight)) {
                    boolean shouldSendNotification = repository.getUserNotificationSetting();
                    if (shouldSendNotification) {
                        // Check if the user has granted permission
                        // Note: We are not handling the permission here since if hte user has their preference as off
                        // or has manually turned off the permission, we can assume that they don't need the feature
                        // but we can show them an error message
                        boolean hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
                        String phoneNumber = currentUser.getPhoneNumber();
                        if (hasPermission && !phoneNumber.trim().isEmpty()) {
                            try {
                                GoalWeight userGoal = repository.getUserGoalWeight();
                                SmsManager smsManager;
                                smsManager = this.getSystemService(SmsManager.class);
                                // We are using hte latest weight as it can surpass the user's current weight!
                                String message = getString(R.string.congratulations_sms, currentUser.getFullName(), userGoal.getCurrentWeight(), weight);
                                if (smsManager != null) {
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null); //
                                    Toast.makeText(this, R.string.sms_sent_successfully, Toast.LENGTH_SHORT).show(); //
                                }
                            } catch (Exception e) {
                                Toast.makeText(this, R.string.failed_to_send_sms, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    Toast.makeText(this, this.getString(R.string.congratulations), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, this.getString(R.string.weight_added_successfully), Toast.LENGTH_SHORT).show();
                }
            }
        }));

        profileImage.setOnClickListener(view -> navigateToProfile());
    }

    /**
     * Returns a boolean indicating whether the user has reached a goal weight depending on the type of goal(loss/gain)
     *
     * @param newWeight The latest logged weight.
     */
    private boolean hasReachGoalWeight(float newWeight) {
        GoalWeight weightGoal = repository.getUserGoalWeight();
        // In case of weight gain we need to check if current logged weight is greater or equal to
        // the goal weight and weight loss if the logged weight is less than or equal
        if (weightGoal.getGoalType() == GoalType.WEIGHT_GAIN) {
            return newWeight >= weightGoal.getGoalWeight();
        } else {
            return newWeight <= weightGoal.getGoalWeight();
        }
    }

    private void navigateToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}