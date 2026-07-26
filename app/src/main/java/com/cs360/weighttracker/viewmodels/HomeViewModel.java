package com.cs360.weighttracker.viewmodels;

import androidx.lifecycle.ViewModel;

import com.cs360.weighttracker.database.MilestoneRepository;

public class HomeViewModel extends ViewModel {

    private final MilestoneRepository repository;

    public HomeViewModel(MilestoneRepository repository) {
        this.repository = repository;
    }

//    public LiveData<LoginStatus> getLoginStatus() {
//        return
//    }

    public void fetchDailyWeight() {
        // TODO: Implementation
    }

    public void addWeight() {
        // TODO: Implementation
    }
}
