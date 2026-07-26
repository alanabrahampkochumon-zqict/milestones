package com.cs360.weighttracker.viewmodels;

import androidx.lifecycle.ViewModel;

import com.cs360.weighttracker.database.MilestoneRepository;

public class ProfileViewModel extends ViewModel {

    private final MilestoneRepository repository;

    public ProfileViewModel(MilestoneRepository repository) {
        this.repository = repository;
    }

//    public LiveData<LoginStatus> getLoginStatus() {
//        return
//    }

    public void updateGoal() { // Use a usecase here?
        // TODO: Implementation
    }

    public void logout() {
        // TODO: Implementation
    }

    public void switchSMSSetting(boolean settingStatus) {
        // TODO: Implementation
    }
}
