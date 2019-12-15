package com.demo.postbookdb.view.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.demo.postbookdb.R;
import com.demo.postbookdb.databinding.FragmentPostListBinding;
import com.demo.postbookdb.service.model.UserPost;
import com.demo.postbookdb.view.adapter.UserPostAdapter;
import com.demo.postbookdb.view.callback.PostClickCallback;
import com.demo.postbookdb.viewmodel.PostListViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/*MVVM View  : Handle user post list and generating observer
to observ changes from viewmodel*/
public class PostListFragment extends Fragment implements PostClickCallback {
    public static final String TAG = "PostListFragment";
    private static final String KEY_USER_ID = "userId";
    private UserPostAdapter userPostAdapter;
    private FragmentPostListBinding binding;

    public static PostListFragment forUserPostList(int userId) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(UserPost userPost) {
        ((MainActivity) getActivity()).showPostCommentFragment(userPost);
    }

    @Override
    public void onFavClick(View view, UserPost userPost) {
        Button btn = (Button) view;
        if (userPost.isFav()) {
            btn.setBackgroundColor(getResources().getColor(R.color.white));
            Snackbar.make(binding.getRoot(), getString(R.string.removed_fav), Snackbar.LENGTH_LONG).show();
        } else {
            btn.setBackgroundColor(getResources().getColor(R.color.grey_light));
            Snackbar.make(binding.getRoot(), getString(R.string.marked_fav), Snackbar.LENGTH_LONG).show();
        }
        userPost.setFav(!userPost.isFav());
        if (userPostAdapter.isFavActive())
            userPostAdapter.refreshUserList(true);
    }

    @Override
    public void onFavBtnClick() {
        if (!userPostAdapter.isFavActive()) {
            userPostAdapter.refreshUserList(true);
            binding.setFavBtBg(getResources().getColor(R.color.grey_light));
            binding.setAllBtBg(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onAllBtnClick() {
        if (userPostAdapter.isFavActive()) {
            userPostAdapter.refreshUserList(false);
            binding.setFavBtBg(getResources().getColor(R.color.white));
            binding.setAllBtBg(getResources().getColor(R.color.grey_light));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.posts));
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_list, container, false);
        userPostAdapter = new UserPostAdapter(this);
        binding.postList.setAdapter(userPostAdapter);
        binding.setCallback(this);
        binding.setIsLoading(true);
        binding.setAllBtBg(getResources().getColor(R.color.grey_light));
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //viewmodel factory object to pass data as argument
        PostListViewModel.Factory factory = new PostListViewModel.Factory(getActivity().getApplication(), getArguments().getInt(KEY_USER_ID));
        final PostListViewModel viewModel = ViewModelProviders.of(this, factory).get(PostListViewModel.class);
        observeViewModel(viewModel);
    }

    //observing changes from viewmodel for post list
    private void observeViewModel(PostListViewModel viewModel) {
        // Update the post list when the data changes
        viewModel.getUserPostListObservable().observe(this, new Observer<List<UserPost>>() {

            @Override
            public void onChanged(@Nullable List<UserPost> posts) {
                binding.setIsLoading(false);
                if (posts != null) {
                    if (posts.size() > 0) {
                        userPostAdapter.setUserPostList(posts);
                    } else {
                        Snackbar.make(binding.getRoot(), getString(R.string.user_not_maped), Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.retry, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ((MainActivity) getActivity()).backToLogin();
                                    }
                                }).show();
                    }
                } else {
                    Snackbar.make(binding.getRoot(), getString(R.string.error), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
