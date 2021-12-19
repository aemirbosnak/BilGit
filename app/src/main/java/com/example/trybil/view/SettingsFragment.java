package com.example.trybil.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trybil.databinding.SettingsFragmentBinding;
import com.example.trybil.model.LocationService;
import com.example.trybil.viewmodel.AuthViewModel;
import com.example.trybil.viewmodel.SettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsFragment extends Fragment {
    private SettingsFragmentBinding settingsFragmentBinding;
    private SettingsViewModel mViewModel;
    private AuthViewModel authViewModel;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
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

        settingsFragmentBinding.updateEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: email should be updated
                String newEmail = settingsFragmentBinding.changeEmailPrompt.getText().toString();
                auth.getCurrentUser().updateEmail(newEmail);
            }
        });

        settingsFragmentBinding.updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: password should be updated
                //
                String newPassword = settingsFragmentBinding.changePasswordPrompt.getText().toString();
                auth.getCurrentUser().updatePassword(newPassword);
            }
        });

        settingsFragmentBinding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.signOut();
            }
        });

        settingsFragmentBinding.locationServices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // stop location service, the isChecked will be
                // true if the switch is in the On position
                if( !isChecked )
                    getActivity().stopService(new Intent(getActivity(), LocationService.class));
                else
                    getActivity().startService(new Intent(getActivity(), LocationService.class));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}