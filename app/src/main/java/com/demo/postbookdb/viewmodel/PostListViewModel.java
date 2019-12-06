package com.demo.postbookdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demo.postbookdb.service.model.UserPost;
import com.demo.postbookdb.service.repository.UserPostCommentRepository;

import java.util.List;

/*MVVM ViewModel : fetch data from server side and generate observable to observe by View*/
public class PostListViewModel extends AndroidViewModel {
    private final MutableLiveData<List<UserPost>> projectListObservable;

    public PostListViewModel(Application application, int userId) {
        super(application);
        projectListObservable = UserPostCommentRepository.getInstance().getUserPostList(userId);
    }

    /*generate observable to observe by View*/
    public LiveData<List<UserPost>> getUserPostListObservable() {
        return projectListObservable;
    }

    /**
     * Factory is used to inject userId at the time of viewmodelprovider creation
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int userId;

        public Factory(@NonNull Application application, int userId) {
            this.application = application;
            this.userId = userId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new PostListViewModel(application, userId);
        }
    }
}
