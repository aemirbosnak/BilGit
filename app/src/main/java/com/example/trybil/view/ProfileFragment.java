package com.example.trybil.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trybil.databinding.ProfileFragmentBinding;
import com.example.trybil.model.FriendAdapter;
import com.example.trybil.model.User;
import com.example.trybil.viewmodel.MainViewModel;
import com.example.trybil.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private ProfileFragmentBinding profileFragmentBinding;
    private MainViewModel mainViewModel;
    ActivityResultLauncher<String> pickImage;
    ArrayList<User> userFriend;
    ArrayList<User> userRequest;
    FriendAdapter friendAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        mainViewModel.getFriends().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> friends) {
                profileFragmentBinding.friendCount.setText("Friends: " + friends.size());
            }
        });

        mainViewModel.getUserRequest().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                friendAdapter = new FriendAdapter(getContext(), getActivity().getApplication(), users);
                profileFragmentBinding.recylclerFriends.setAdapter(friendAdapter);
                profileFragmentBinding.recylclerFriends.setLayoutManager(new LinearLayoutManager(getContext()));
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
                if (bitmap != null) {
                    profileFragmentBinding.imageView.setImageBitmap(bitmap);
                }
                else {
                    profileFragmentBinding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.avatar));

                }
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