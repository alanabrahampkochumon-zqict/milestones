package com.cs360.weighttracker.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.database.status.LoginStatus;
import com.cs360.weighttracker.database.status.RegisterStatus;

public class AuthViewModel extends ViewModel {

    private final MilestoneRepository repository;

    private final MutableLiveData<LoginStatus> loginResult = new MutableLiveData<>(LoginStatus.NO_STATUS);
    private final MutableLiveData<RegisterStatus> registerResult = new MutableLiveData<>(RegisterStatus.NO_STATUS);


    public AuthViewModel(MilestoneRepository repository) {
        this.repository = repository;
    }


    /**
     * Get the current login status as an observable live data.
     */
    public LiveData<LoginStatus> getLoginStatus() {
        return loginResult;
    }

    /**
     * Get the current registration status as an observable live data.
     */
    public LiveData<RegisterStatus> getRegistrationStatus() {
        return registerResult;
    }


    public void login(String username, String password) {
        LoginStatus status = repository.loginUser(username, password);
        loginResult.setValue(status);
    }

    public void register(String username, String password) {
        RegisterStatus status = repository.registerUser(username, password);
        registerResult.setValue(status);
    }

    public void updateProfile(String fullName, float currentWeight, float goalWeight) {
        // TODO: Implementation
    }
}
