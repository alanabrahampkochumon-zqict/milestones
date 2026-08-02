package com.cs360.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cs360.weighttracker.components.AddWeightDialog;
import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.models.User;
import com.cs360.weighttracker.utils.PasswordVisibilityToggler;

public class HomeActivity extends AppCompatActivity {


    TextView fullNameTextView;
    Button trackWeightButton;
    ImageView profileImage;
    RecyclerView progressHistoryRecyclerView;


    MilestoneRepository repository;
    User currentUser;

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
    }


    /**
     * Sets up the event listeners necessary for all the views.
     */
    private void setupEvents() {
        // Finish will pop off this activity from the backstack leaving only with the Login activity
        // from the navigation will take place
        trackWeightButton.setOnClickListener(view -> {
            Log.d("Track Weight", "Tracking weight");
            AddWeightDialog dialog = new AddWeightDialog();
            dialog.show(getSupportFragmentManager(), Constants.ADD_NEW_WEIGHT_REQUEST_KEY);
        });

        // Add event listener for the dialog
        getSupportFragmentManager().setFragmentResultListener(Constants.ADD_NEW_WEIGHT_REQUEST_KEY, this, ((requestKey, result) -> {
            float weight = result.getFloat(Constants.NEW_WEIGHT_BUNDLE_KEY);
            repository.logDailyWeight(weight);

            // TODO: Refresh data
            Toast.makeText(this, this.getString(R.string.weight_added_successfully), Toast.LENGTH_SHORT).show();
        }));

        profileImage.setOnClickListener(view -> navigateToProfile());
    }

    private void navigateToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}