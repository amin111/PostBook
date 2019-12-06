package com.demo.postbookdb.view.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.postbookdb.R;
import com.demo.postbookdb.service.model.UserPost;

/*Main PostBook activity which handle fragment and basic HK*/
public class MainActivity extends AppCompatActivity {

    private static long back_pressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the Login fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LoginFragment.userLogin(), LoginFragment.TAG).commit();
        }
    }


    //Shows the user post fragment
    public void showPostFragment(int userId) {
        PostListFragment postListFragment = PostListFragment.forUserPostList(userId);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.fragment_container, postListFragment, PostListFragment.TAG)
                .commit();
    }

    //Shows the post comments fragment
    public void showPostCommentFragment(UserPost userPost) {
        PostCommentListFragment postCommentListFragment = PostCommentListFragment.forPostComment(userPost);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("postbook")
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.fragment_container, postCommentListFragment, PostCommentListFragment.TAG)
                .commit();
    }

    //return to login fragment
    public void backToLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.fragment_container, LoginFragment.userLogin(), "LoginFragment")
                .commit();
    }

    //set title on actionbar
    public void setActionBarTitle(String Title) {
        getSupportActionBar().setTitle(Title);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            super.onBackPressed();
        else {
            if (back_pressed + 2000 > System.currentTimeMillis())
                super.onBackPressed();
            else
                Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
