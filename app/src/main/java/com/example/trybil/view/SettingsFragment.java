package com.example.trybil.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trybil.R;
import com.example.trybil.databinding.LoginFragmentBinding;
import com.example.trybil.databinding.RegisterFragmentBinding;
import com.example.trybil.databinding.SearchFragmentBinding;
import com.example.trybil.databinding.SettingsFragmentBinding;
import com.example.trybil.viewmodel.AuthViewModel;
import com.example.trybil.viewmodel.SettingsViewModel;

public class SettingsFragment extends Fragment {
    private SettingsFragmentBinding settingsFragmentBinding;
    private SettingsViewModel mViewModel;
    private AuthViewModel authViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        authViewModel.getLoggedStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    startActivity(new Intent(getContext(), AuthActivity.class));
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        settingsFragmentBinding = SettingsFragmentBinding.inflate(inflater, container, false);
        return settingsFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsFragmentBinding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.signOut();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}