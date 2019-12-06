package com.demo.postbookdb.service.model;

import com.google.gson.annotations.SerializedName;

/*pojo for post comment details*/
public class PostComment {
    @SerializedName("postId")
    public int postId;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("body")
    public String body;
}
