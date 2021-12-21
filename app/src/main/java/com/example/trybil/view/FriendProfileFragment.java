package com.example.trybil.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private AddListener addListener;
    private RemoveListener removeListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        addListener = new AddListener();
        removeListener = new RemoveListener();

        mainViewModel.getSearchUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                friendProfileFragmentBinding.userName.setText(user.getUsername());
                friendProfileFragmentBinding.department.setText(user.getDepartment());

                if (user.getPriv()) {
                    friendProfileFragmentBinding.privacy.setText("Private Profile");
                    friendProfileFragmentBinding.privacy.setTextColor(Color.RED);
                }
                else {
                    friendProfileFragmentBinding.privacy.setText("Public Profile");
                    friendProfileFragmentBinding.privacy.setTextColor(Color.GREEN);
                }
            }
        });

        mainViewModel.getIsFriend().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    friendProfileFragmentBinding.friendRequest.setText("Remove Friend");
                    friendProfileFragmentBinding.friendRequest.setOnClickListener(removeListener);
                }
                else {
                    friendProfileFragmentBinding.friendRequest.setText("Add Friend");
                    friendProfileFragmentBinding.friendRequest.setOnClickListener(addListener);
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

        mainViewModel.getSrcFriendCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                friendProfileFragmentBinding.friendCount.setText("Friends: " + integer);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        friendProfileFragmentBinding = FriendProfileFragmentBinding.inflate(inflater, container, false);
        return friendProfileFragmentBinding.getRoot();
    }

    class AddListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mainViewModel.addFriend();
        }
    }

    class RemoveListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Friend Removed", Toast.LENGTH_SHORT).show();
            mainViewModel.removeFriend();
        }
    }
}