package com.example.trybil.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trybil.databinding.ProfileFragmentBinding;
import com.example.trybil.model.User;
import com.example.trybil.viewmodel.MainViewModel;
import com.example.trybil.viewmodel.ProfileViewModel;
import com.example.trybil.R;

public class ProfileFragment extends Fragment {
    private ProfileFragmentBinding profileFragmentBinding;
    private ProfileViewModel mViewModel;
    private MainViewModel mainViewModel;

    public static ProfileFragment newInstance() { return new ProfileFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileFragmentBinding = ProfileFragmentBinding.inflate(inflater, container, false);

        mainViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                profileFragmentBinding.userName.setText(user.getUsername());
                profileFragmentBinding.department.setText(user.getDepartment());
                mainViewModel.getUser().removeObservers(getViewLifecycleOwner());
            }
        });
        return profileFragmentBinding.getRoot();
    }
}
