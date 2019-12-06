package com.demo.postbookdb.view.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.postbookdb.R;
import com.demo.postbookdb.databinding.PostListItemBinding;
import com.demo.postbookdb.service.model.UserPost;
import com.demo.postbookdb.view.callback.PostClickCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*generating post list and using
recycler viewholder pattern adapter*/
public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.UserPostViewHolder> {

    @Nullable
    private final PostClickCallback postClickCallback;
    List<UserPost> postList;
    List<UserPost> postListFiltered;
    private boolean isFavActive = false;
    private ViewGroup parent;

    public UserPostAdapter(@Nullable PostClickCallback postClickCallback) {
        this.postClickCallback = postClickCallback;
    }

    public void setUserPostList(final List<UserPost> userPostList) {
        if (this.postList == null) {
            this.postList = userPostList;
            notifyItemRangeInserted(0, postList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return UserPostAdapter.this.postList.size();
                }

                @Override
                public int getNewListSize() {
                    return postList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return UserPostAdapter.this.postList.get(oldItemPosition).id == postList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    UserPost userPost = postList.get(newItemPosition);
                    UserPost old = postList.get(oldItemPosition);
                    return userPost.id == old.id && Objects.equals(userPost.title, old.title);
                }
            });
            this.postList = postList;
            result.dispatchUpdatesTo(this);
        }
        this.postListFiltered = postList;
    }

    @Override
    public UserPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PostListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.post_list_item, parent, false);

        binding.setCallback(postClickCallback);
        this.parent = parent;
        return new UserPostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UserPostViewHolder holder, int position) {
        holder.binding.setUserpost(postList.get(position));
        if (postList.get(position).isFav())
            holder.binding.favbtn.setBackgroundColor(parent.getResources().getColor(R.color.grey_light));
        else
            holder.binding.favbtn.setBackgroundColor(parent.getResources().getColor(R.color.white));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    public boolean isFavActive() {
        return isFavActive;
    }


    public void refreshUserList(boolean fetchFav) {
        this.isFavActive = fetchFav;
        List<UserPost> filteredList = new ArrayList<>();
        this.postList = this.postListFiltered;
        if (fetchFav) {
            for (UserPost post : this.postListFiltered) {
                if (post.isFav())
                    filteredList.add(post);
            }
            this.postList = filteredList;
        }
        notifyDataSetChanged();
    }


    static class UserPostViewHolder extends RecyclerView.ViewHolder {

        final PostListItemBinding binding;

        public UserPostViewHolder(PostListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
