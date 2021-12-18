package com.example.trybil.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trybil.databinding.FriendProfileFragmentBinding;
import com.example.trybil.model.User;
import com.example.trybil.viewmodel.MainViewModel;

public class FriendProfileFragment extends Fragment {
    private FriendProfileFragmentBinding friendProfileFragmentBinding;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        mainViewModel.getSearchUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                friendProfileFragmentBinding.userName.setText(user.getUsername());
                friendProfileFragmentBinding.department.setText(user.getDepartment());
            }
        });

        mainViewModel.getSearchPic().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                friendProfileFragmentBinding.imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        friendProfileFragmentBinding = FriendProfileFragmentBinding.inflate(inflater, container, false);
        return friendProfileFragmentBinding.getRoot();
    }
}