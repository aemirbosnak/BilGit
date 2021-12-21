package com.example.trybil.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.trybil.R;
import com.example.trybil.databinding.LoginFragmentBinding;
import com.example.trybil.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private LoginFragmentBinding loginFragmentBinding;
    private AuthViewModel mViewModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        mViewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    if(firebaseUser.getEmail() == null || firebaseUser.getEmail().isEmpty()) {
                        Toast.makeText(getContext(), "Logged in Anonymously", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Welcome " + firebaseUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }
            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        loginFragmentBinding = LoginFragmentBinding.inflate(inflater, container, false);
        return loginFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        loginFragmentBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginFragmentBinding.editTextEmail.getText().toString();
                String pass = loginFragmentBinding.editTextPassword.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()){
                    mViewModel.signIn(email , pass);
                }
            }
        });

        loginFragmentBinding.textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        loginFragmentBinding.buttonLoginAnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.loginAnon();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loginFragmentBinding = null;
        onDestroy();
    }
}