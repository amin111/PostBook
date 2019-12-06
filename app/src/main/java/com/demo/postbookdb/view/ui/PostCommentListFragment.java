package com.demo.postbookdb.view.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.demo.postbookdb.R;
import com.demo.postbookdb.databinding.FragmentPostCommentsBinding;
import com.demo.postbookdb.service.model.PostComment;
import com.demo.postbookdb.service.model.UserPost;
import com.demo.postbookdb.view.adapter.PostCommentAdapter;
import com.demo.postbookdb.viewmodel.PostCommentViewModel;

import java.util.List;

/*MVVM View  : Handle comment list and generating observer
to observe changes from viewmodel*/
public class PostCommentListFragment extends Fragment {
    private static final String KEY_USERPOST = "userPost";
    public static final String TAG = "PostCommentListFragment";
    private PostCommentAdapter postCommentAdapter;
    private FragmentPostCommentsBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.comments));
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_comments, container, false);
        postCommentAdapter = new PostCommentAdapter();
        binding.postCommentList.setAdapter(postCommentAdapter);
        binding.setIsLoading(true);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PostCommentViewModel.Factory factory = new PostCommentViewModel.Factory(getActivity().getApplication(), (UserPost) getArguments().getSerializable(KEY_USERPOST));
        final PostCommentViewModel viewModel = ViewModelProviders.of(this, factory).get(PostCommentViewModel.class);
        binding.setPostCommentViewModel(viewModel);
        binding.setIsLoading(true);
        observeViewModel(viewModel);
    }

    /*observing list of comments from viewmodel*/
    private void observeViewModel(PostCommentViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getObservablePostComments().observe(this, new Observer<List<PostComment>>() {
            @Override
            public void onChanged(@Nullable List<PostComment> posts) {
                if (posts != null) {
                    binding.setIsLoading(false);
                    postCommentAdapter.setPostCommentList(posts);
                }
            }
        });
    }

    //generating fragment instanse and passed argument to next fragment
    public static PostCommentListFragment forPostComment(UserPost userPost) {
        PostCommentListFragment fragment = new PostCommentListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_USERPOST, userPost);
        fragment.setArguments(args);
        return fragment;
    }
}
