package com.demo.postbookdb.service.repository;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.demo.postbookdb.service.model.PostComment;
import com.demo.postbookdb.service.model.UserPost;
import com.demo.postbookdb.viewmodel.PostCommentViewModel;
import com.demo.postbookdb.viewmodel.PostListViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class UserPostCommentRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    APIClient apiClient;
    @Mock
    UserPostCommentRepository userPostCommentRepository;
    private final int USER_ID = 1;
    private final int POST_ID = 1;

    private PostListViewModel postListViewModel;
    private PostCommentViewModel postCommentViewModel;
    private Application application;
    @Mock
    Observer<List<UserPost>> observer;
    @Mock
    Observer<List<PostComment>> observerComment;
    @Mock
    MutableLiveData<List<UserPost>> postMutabledata;
    @Mock
    MutableLiveData<List<PostComment>> commentMutableData;
    @Mock
    List<UserPost> postData;
    @Mock
    List<PostComment> commentData;


    @Before
    public void setUpPost() throws Exception {
        MockitoAnnotations.initMocks(this);
        postListViewModel = new PostListViewModel(application,USER_ID);
        postListViewModel.getUserPostListObservable().observeForever(observer);
    }

    @Before
    public void setUpComment() throws Exception {
        MockitoAnnotations.initMocks(this);
        postCommentViewModel = new PostCommentViewModel(application,new UserPost());
        postCommentViewModel.getObservablePostComments().observeForever(observerComment);
    }

    @Test
    public void testApiFetchPostDataError() {
        when(userPostCommentRepository.getUserPostList(USER_ID)).thenReturn(null);
        assertNotNull(postListViewModel.getUserPostListObservable());
        assertTrue(postListViewModel.getUserPostListObservable().hasObservers());
    }

    @Test
    public void testApiFetchCommentDataError() {
        when(userPostCommentRepository.getPostComments(POST_ID)).thenReturn(null);
        assertNotNull(postCommentViewModel.getObservablePostComments());
        assertTrue(postCommentViewModel.getObservablePostComments().hasObservers());
    }

    @Test
    public void testApiFetchPostDataSuccess() {
        // Mock API response
        when(userPostCommentRepository.getUserPostList(USER_ID)).thenReturn(postMutabledata);
        postListViewModel.getUserPostListObservable();
        verify(observer).onChanged(postData);
    }

    @Test
    public void testApiFetchCommentDataSuccess() {
        // Mock API response
        when(userPostCommentRepository.getPostComments(POST_ID)).thenReturn(commentMutableData);
        postListViewModel.getUserPostListObservable();
        verify(observerComment).onChanged(commentData);
    }


    @After
    public void tearDown() throws Exception {
        apiClient = null;
        userPostCommentRepository = null;
        postListViewModel = null;
    }
}