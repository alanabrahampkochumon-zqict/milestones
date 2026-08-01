package com.cs360.weighttracker.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.database.status.LoginStatus;

public class AuthViewModel extends ViewModel {

    private final MilestoneRepository repository;

    private final MutableLiveData<LoginStatus> loginResult = new MutableLiveData<>(LoginStatus.PLACEHOLDER);

    
    public AuthViewModel(MilestoneRepository repository) {
        this.repository = repository;
    }


    public LiveData<LoginStatus> getLoginStatus() {
        return loginResult;
    }

    public void login(String username, String password) {
        LoginStatus status = repository.loginUser(username, password);
        loginResult.setValue(status);
    }

    public void register() {
        // TODO: Implementation
    }

    public void updateProfile(String fullName, float currentWeight, float goalWeight) {
        // TODO: Implementation
    }
}
