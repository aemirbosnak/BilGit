package com.example.trybil.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.trybil.R;
import com.example.trybil.databinding.SearchFragmentBinding;
import com.example.trybil.model.User;
import com.example.trybil.viewmodel.MainViewModel;
import com.example.trybil.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment {

    private MainViewModel mViewModel;
    private SearchFragmentBinding searchFragmentBinding;
    private MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mViewModel.getSearchUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                searchFragmentBinding.cardv.setVisibility(View.VISIBLE);
                searchFragmentBinding.txtUsername.setText(user.getUsername());
                searchFragmentBinding.txtDepartment.setText(user.getDepartment());
                Toast.makeText(getContext(), "YUUUH", Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getSearchPic().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                searchFragmentBinding.imgSearch.setImageBitmap(bitmap);
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
    }
}