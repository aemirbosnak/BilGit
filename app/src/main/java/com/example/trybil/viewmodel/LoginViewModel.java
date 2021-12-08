package com.example.trybil.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.trybil.model.User;
import com.example.trybil.model.UserRepository;

public class LoginViewModel extends ViewModel {
    User user;
    UserRepository userRepository = new UserRepository();
    //SavedStateHandle savedStateHandle;

    public void getUser() {
        user = userRepository.getUser();
    }

    public boolean authUser(String email, String password) {
        getUser();

        return user.getUserEmail().equals(email) && user.getUserPassword().equals(password);
    }
}