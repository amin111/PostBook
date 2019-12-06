package com.demo.postbookdb.service.repository;

import com.demo.postbookdb.service.model.PostComment;
import com.demo.postbookdb.service.model.UserPost;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*Retrofit API Interface generating service method */
interface UserPostCommentService {

    /*get user post list service*/
    @GET("/posts?")
    Call<List<UserPost>>  doGetUserPostListResources(@Query("userId") int userId);

    /*get post comment list service*/
    @GET("/comments?")
    Call<List<PostComment>>  doGetUserPostCommentResources(@Query("postId") int postId);

}
