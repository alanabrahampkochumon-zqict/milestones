package com.cs360.weighttracker.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cs360.weighttracker.database.MilestoneRepository;
import com.cs360.weighttracker.viewmodels.AuthViewModel;

// Since out viewmodel has custom parameters we need to instantiate it with a viewmodel factory
// and attach it to the lifecycle of the application so that it will survive configuration changes.
public class AuthViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public AuthViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            MilestoneRepository repository = MilestoneRepository.getInstance(context);
            return (T) new AuthViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}