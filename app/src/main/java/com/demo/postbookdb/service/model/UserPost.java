package com.demo.postbookdb.service.model;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*User Pojo to get and set details*/
public class UserPost extends BaseObservable implements Serializable {
    @SerializedName("userId")
    public int userId = -1;
    @SerializedName("id")
    public int id = -1;
    @SerializedName("title")
    public String title;
    @SerializedName("body")
    public String body;
    public boolean isFav = false;

    public UserPost() {
    }

    public UserPost(int userId) {
        this.userId = userId;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        this.isFav = fav;
    }

    public String getUserId() {
        return String.valueOf(userId);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /*userid text watcher*/
    public void afterUserIdChanged(CharSequence s) {
        if (!TextUtils.isEmpty(s))
            this.setUserId(Integer.parseInt(s.toString()));
    }

    /*validation useally it shd be in special validator file*/
    public boolean isUserIdValid() {
        return !TextUtils.isEmpty(this.getUserId()) && this.getUserId() != "-1";
    }
}
