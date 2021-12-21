package com.example.trybil.view;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trybil.R;
import com.example.trybil.databinding.SettingsFragmentBinding;
import com.example.trybil.model.LocationService;
import com.example.trybil.viewmodel.AuthViewModel;
import com.example.trybil.viewmodel.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {
    private SettingsFragmentBinding settingsFragmentBinding;
    private AuthViewModel authViewModel;
    private MainViewModel mainViewModel;
    private FirebaseUser user;
    private boolean serviceRunning;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        user = FirebaseAuth.getInstance().getCurrentUser();
        authViewModel.getLoggedStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    Intent intent = new Intent(getContext(), AuthActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        mainViewModel.getPriv().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    settingsFragmentBinding.privateProfile.setChecked(true);
                else
                    settingsFragmentBinding.privateProfile.setChecked(false);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        settingsFragmentBinding = SettingsFragmentBinding.inflate(inflater, container, false);

        if (isRunning(LocationService.class))
            settingsFragmentBinding.locationServices.setChecked(true);
        else
            settingsFragmentBinding.locationServices.setChecked(false);

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

        settingsFragmentBinding.notificationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        settingsFragmentBinding.changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText newMail = new EditText(v.getContext());
                final EditText crrPass = new EditText(v.getContext());
                newMail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                crrPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                TextView newEText = new TextView(v.getContext());
                TextView crrPText = new TextView(v.getContext());
                newEText.setText("Your new email");
                crrPText.setText("Your current password");

                LinearLayout lay = new LinearLayout(v.getContext());
                lay.setOrientation(LinearLayout.VERTICAL);
                lay.addView(newEText);
                lay.addView(newMail);
                lay.addView(crrPText);
                lay.addView(crrPass);

                AlertDialog.Builder changeMail = new AlertDialog.Builder(v.getContext())
                        .setTitle("Change Password")
                        .setView(lay)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newEmail = newMail.getText().toString().trim();
                                String crrPassword = crrPass.getText().toString().trim();
                                try {
                                    changeEmail(newEmail, crrPassword, v.getContext());
                                }
                                catch (IllegalArgumentException e) {
                                    Toast.makeText(v.getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setIcon(R.drawable.bilgit_launcher_icon);
                changeMail.create().show();
            }
        });

        settingsFragmentBinding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText oldPass = new EditText(v.getContext());
                final EditText newPass = new EditText(v.getContext());
                oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                TextView oldPText = new TextView(v.getContext());
                TextView newPText = new TextView(v.getContext());
                oldPText.setText("Your current password");
                newPText.setText("Your new password");

                LinearLayout lay = new LinearLayout(v.getContext());
                lay.setOrientation(LinearLayout.VERTICAL);
                lay.addView(oldPText);
                lay.addView(oldPass);
                lay.addView(newPText);
                lay.addView(newPass);
                AlertDialog.Builder changePass = new AlertDialog.Builder(v.getContext())
                        .setTitle("Change Password")
                        .setView(lay)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String oldPassword = oldPass.getText().toString().trim();
                                String newPassword = newPass.getText().toString().trim();
                                try {
                                    changePassword(oldPassword, newPassword, v.getContext());
                                }
                                catch (IllegalArgumentException e) {
                                    Toast.makeText(v.getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setIcon(R.drawable.bilgit_launcher_icon);
                changePass.create().show();
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

        settingsFragmentBinding.privateProfile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainViewModel.changePriv(isChecked);
            }
        });
    }

    private void changeEmail(String newEmail, String currentPassword, Context context) {
        final String email = user.getEmail();
        AuthCredential authCredential = EmailAuthProvider.getCredential(email, currentPassword);

        user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Email updated", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "Email cannot be updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(context, "Auth failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changePassword(String oldPassword, String newPassword, Context context) {
        final String email = user.getEmail();
        AuthCredential authCredential = EmailAuthProvider.getCredential(email, oldPassword);

        user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Password updated", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "Password cannot be updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(context, "Auth failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isRunning(Class<?> serviceClass)  {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}