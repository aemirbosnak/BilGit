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

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private ProfileFragmentBinding profileFragmentBinding;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getFriends().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> friends) {
                profileFragmentBinding.editTextTextMultiLine.setText("Friends: " + friends.size());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileFragmentBinding = ProfileFragmentBinding.inflate(inflater, container, false);
        return profileFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}