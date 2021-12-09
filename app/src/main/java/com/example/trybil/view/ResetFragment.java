package com.example.trybil.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trybil.databinding.ResetFragmentBinding;
import com.example.trybil.viewmodel.AuthViewModel;

public class ResetFragment extends Fragment {
    private ResetFragmentBinding resetFragmentBinding;
    private AuthViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resetFragmentBinding = ResetFragmentBinding.inflate(inflater, container, false);
        return resetFragmentBinding.getRoot();
    }
}