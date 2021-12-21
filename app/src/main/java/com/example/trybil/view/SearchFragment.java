package com.example.trybil.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.trybil.R;
import com.example.trybil.databinding.SearchFragmentBinding;
import com.example.trybil.model.User;
import com.example.trybil.viewmodel.MainViewModel;

public class SearchFragment extends Fragment {

    private MainViewModel mViewModel;
    private SearchFragmentBinding searchFragmentBinding;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        mViewModel.getSearchUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                searchFragmentBinding.cardv.setVisibility(View.VISIBLE);
                searchFragmentBinding.txtUsername.setText(user.getUsername());
                searchFragmentBinding.txtDepartment.setText(user.getDepartment());
            }
        });

        mViewModel.getIsFriend().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    searchFragmentBinding.txtFriend.setText("You Are Friends");
                }
                else {
                    searchFragmentBinding.txtFriend.setText("You Are Not Friends");
                }
            }
        });

        mViewModel.getSearchPic().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                if(bitmap != null) {
                    searchFragmentBinding.imgSearch.setImageBitmap(bitmap);
                }
                else {
                    searchFragmentBinding.imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        searchFragmentBinding = SearchFragmentBinding.inflate(inflater, container, false);
        return searchFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        searchFragmentBinding.searchProfile.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.searchUser(searchFragmentBinding.searchProfile.getQuery().toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchFragmentBinding.profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.friendProfileFragment);
            }
        });
    }
}