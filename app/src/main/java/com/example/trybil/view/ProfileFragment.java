package com.example.trybil.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trybil.databinding.ProfileFragmentBinding;
import com.example.trybil.model.User;
import com.example.trybil.viewmodel.MainViewModel;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private ProfileFragmentBinding profileFragmentBinding;
    private MainViewModel mainViewModel;
    ActivityResultLauncher<String> pickImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getFriends().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> friends) {
                profileFragmentBinding.friendCount.setText("Friends: " + friends.size());
            }
        });

        mainViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                profileFragmentBinding.userName.setText(user.getUsername());
                profileFragmentBinding.department.setText(user.getDepartment());
            }
        });

        mainViewModel.getPicture().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                profileFragmentBinding.imageView.setImageBitmap(bitmap);
            }
        });

        pickImage = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            mainViewModel.uploadPic(result);
                        }
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
        profileFragmentBinding.btnUp.setOnClickListener(v -> pickImage.launch("image/"));
    }
}