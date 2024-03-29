package com.oconte.david.go4lunch.auth;

import androidx.lifecycle.ViewModel;

import com.oconte.david.go4lunch.repositories.UserRepository;
import com.oconte.david.go4lunch.repositories.UserRepositoryImpl;

public class AuthViewModel extends ViewModel {

    private final UserRepository userRepository;

    public AuthViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser() {
        userRepository.createUser();
    }
}
