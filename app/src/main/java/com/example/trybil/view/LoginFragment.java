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
import com.example.trybil.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private LoginFragmentBinding loginFragmentBinding;
    private AuthViewModel mViewModel;
    private NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        loginFragmentBinding = LoginFragmentBinding.inflate(inflater, container, false);

        mViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getContext(), MainActivity.class));
                    //getActivity().finish();
                }
            }
        });

        /*
        mViewModel.getTestString().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loginFragmentBinding.editTextEmail.setText("fff");
            }
        });
         */


        loginFragmentBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginFragmentBinding.editTextEmail.getText().toString();
                String pass = loginFragmentBinding.editTextPassword.getText().toString();

                //loginFragmentBinding.editTextEmail.setText(mViewModel.getTestString().toString());
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

        return loginFragmentBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loginFragmentBinding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}