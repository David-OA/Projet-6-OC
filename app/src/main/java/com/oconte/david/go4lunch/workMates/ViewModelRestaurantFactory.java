package com.oconte.david.go4lunch.workMates;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class ViewModelRestaurantFactory implements ViewModelProvider.Factory {

    private final UserRepository userRepository;

    public ViewModelRestaurantFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WorkMatesViewModel.class)) {
            return (T) new WorkMatesViewModel(userRepository);
        }
        throw new IllegalArgumentException("ViewModel Not Found");
    }
}