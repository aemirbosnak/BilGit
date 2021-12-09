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
import android.widget.Toast;

import com.example.trybil.R;
import com.example.trybil.databinding.LoginFragmentBinding;
import com.example.trybil.databinding.RegisterFragmentBinding;
import com.example.trybil.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    private RegisterFragmentBinding registerFragmentBinding;
    private AuthViewModel mViewModel;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        mViewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    Toast.makeText(getContext(), "So:" + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getContext(), MainActivity.class));
                    //getActivity().finish();
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        registerFragmentBinding = RegisterFragmentBinding.inflate(inflater, container, false);
        return registerFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerFragmentBinding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerFragmentBinding.editTextEmailReg.getText().toString();
                String pass = registerFragmentBinding.editTextPasswordReg.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()){
                    mViewModel.register(email , pass);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        registerFragmentBinding = null;
    }
}