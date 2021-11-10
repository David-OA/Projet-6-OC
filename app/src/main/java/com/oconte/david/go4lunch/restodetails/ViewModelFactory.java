package com.oconte.david.go4lunch.restodetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.oconte.david.go4lunch.listView.ListRestaurantViewModel;
import com.oconte.david.go4lunch.workMates.UserRepository;
import com.oconte.david.go4lunch.workMates.WorkMatesViewModel;

import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RestaurantDetailRepository restaurantDetailRepository;
    private final UserRepository userRepository;

    public ViewModelFactory(RestaurantDetailRepository restaurantDetailRepository, UserRepository userRepository) {
        this.restaurantDetailRepository = restaurantDetailRepository;
        this.userRepository = userRepository;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailsRestaurantViewModel.class)) {
            return (T) new DetailsRestaurantViewModel(restaurantDetailRepository, userRepository);
        }
        if (modelClass.isAssignableFrom(ListRestaurantViewModel.class)) {
            return (T) new ListRestaurantViewModel(userRepository);
        }
        if (modelClass.isAssignableFrom(WorkMatesViewModel.class)) {
            return (T) new WorkMatesViewModel(userRepository);
        }
        throw new IllegalArgumentException("ViewModel Not Found");
    }
}