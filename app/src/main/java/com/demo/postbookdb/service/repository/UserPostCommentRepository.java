package com.demo.postbookdb.service.repository;

import androidx.lifecycle.MutableLiveData;

import com.demo.postbookdb.service.model.PostComment;
import com.demo.postbookdb.service.model.UserPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*MVVM Repository File
for fetching the user post & its comments list*/

public class UserPostCommentRepository {
    private static UserPostCommentRepository projectRepository;
    private UserPostCommentService projectService;

    private UserPostCommentRepository() {
        projectService = APIClient.getClient().create(UserPostCommentService.class);
    }

    public synchronized static UserPostCommentRepository getInstance() {
        if (projectRepository == null) {
            if (projectRepository == null) {
                projectRepository = new UserPostCommentRepository();
            }
        }
        return projectRepository;
    }

    /*get user post list from server*/
    public MutableLiveData<List<UserPost>> getUserPostList(int userId) {
        final MutableLiveData<List<UserPost>> data = new MutableLiveData<>();

        projectService.doGetUserPostListResources(userId).enqueue(new Callback<List<UserPost>>() {

            @Override
            public void onResponse(Call<List<UserPost>> call, Response<List<UserPost>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<UserPost>> call, Throwable t) {
                data.setValue(null);
                call.cancel();
            }
        });

        return data;
    }

    /*get user comment list from server*/
    public MutableLiveData<List<PostComment>> getPostComments(int postID) {
        final MutableLiveData<List<PostComment>> data = new MutableLiveData<>();

        projectService.doGetUserPostCommentResources(postID).enqueue(new Callback<List<PostComment>>() {
            @Override
            public void onResponse(Call<List<PostComment>> call, Response<List<PostComment>> response) {
                simulateDelay();
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostComment>> call, Throwable t) {
                data.setValue(null);
                call.cancel();
            }
        });

        return data;
    }

    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
