package com.demo.postbookdb.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.demo.postbookdb.R;
import com.demo.postbookdb.databinding.FragmentLoginBinding;
import com.demo.postbookdb.service.model.UserPost;
import com.demo.postbookdb.view.callback.LoginClickCallback;

/*MVVM View : use of fragment instead of activity to make
it more flexible and scalable*/
public class LoginFragment extends Fragment implements LoginClickCallback {

    public static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;

    public static LoginFragment userLogin() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onClick(UserPost userPost) {
        if (userPost.isUserIdValid()) {
            ((MainActivity) getActivity()).showPostFragment(Integer.parseInt(userPost.getUserId()));
        } else {
            binding.userIdTxt.setError(getString(R.string.required));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setLoginCallback(this);
        binding.setUserPost(new UserPost());
        return binding.getRoot();
    }
}
