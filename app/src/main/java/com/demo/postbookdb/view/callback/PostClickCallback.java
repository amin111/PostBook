package com.demo.postbookdb.view.callback;

import android.view.View;

import com.demo.postbookdb.service.model.UserPost;

/*callback from post item list XML*/
public interface PostClickCallback {
    void onClick(UserPost userPost);

    void onFavClick(View view, UserPost userPost);

    void onFavBtnClick();

    void onAllBtnClick();
}
