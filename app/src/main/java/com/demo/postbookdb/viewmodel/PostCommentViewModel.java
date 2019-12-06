package com.demo.postbookdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demo.postbookdb.service.model.PostComment;
import com.demo.postbookdb.service.model.UserPost;
import com.demo.postbookdb.service.repository.UserPostCommentRepository;

import java.util.List;

/*MVVM ViewModel : fetch data from server side and generate observable to observe by View*/
public class PostCommentViewModel extends AndroidViewModel {
    private final LiveData<List<PostComment>> postCommentObservable;
    public ObservableField<List<PostComment>> postComment = new ObservableField<>();
    public ObservableField<UserPost> userPost = new ObservableField<>();

    public PostCommentViewModel(@NonNull Application application, UserPost userPost) {
        super(application);
        this.userPost.set(userPost);
        postCommentObservable = UserPostCommentRepository.getInstance().getPostComments(userPost.id);
    }

    /*generate observable to observe by View*/
    public LiveData<List<PostComment>> getObservablePostComments() {
        return postCommentObservable;
    }

    /**
     * Factory is used to inject UserPost object at the time of viewmodelprovider creation
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final UserPost userPost;

        public Factory(@NonNull Application application, UserPost userPost) {
            this.application = application;
            this.userPost = userPost;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PostCommentViewModel(application, userPost);
        }
    }
}
