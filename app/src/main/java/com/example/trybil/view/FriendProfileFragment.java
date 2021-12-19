package com.example.trybil.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trybil.R;
import com.example.trybil.databinding.FriendProfileFragmentBinding;
import com.example.trybil.model.User;
import com.example.trybil.viewmodel.MainViewModel;

import java.util.ArrayList;

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

        mainViewModel.getIsFriend().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    friendProfileFragmentBinding.friendRequest.setText("Remove Friend");
                }
                else {
                    friendProfileFragmentBinding.friendRequest.setText("Add Friend");
                }
            }
        });

        mainViewModel.getSearchPic().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                if(bitmap != null) {
                    friendProfileFragmentBinding.imageView.setImageBitmap(bitmap);
                }
                else {
                    friendProfileFragmentBinding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
                }
            }
        });

        mainViewModel.getFriends().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> friends) {
                friendProfileFragmentBinding.friendCount.setText("Friends: " + friends.size());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        friendProfileFragmentBinding.friendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.addFriend();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        friendProfileFragmentBinding = FriendProfileFragmentBinding.inflate(inflater, container, false);
        return friendProfileFragmentBinding.getRoot();
    }
}