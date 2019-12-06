package com.demo.postbookdb.view.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.postbookdb.R;
import com.demo.postbookdb.databinding.PostCommentListItemBinding;
import com.demo.postbookdb.service.model.PostComment;

import java.util.List;
import java.util.Objects;

/*generating comment list and using
recycler viewholder pattern adapter*/
public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.PostCommentViewHolder> {

    List<? extends PostComment> postCommentList;

    public void setPostCommentList(final List<? extends PostComment> postCommnetList) {
        if (this.postCommentList == null) {
            this.postCommentList = postCommnetList;
            notifyItemRangeInserted(0, postCommentList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return PostCommentAdapter.this.postCommentList.size();
                }

                @Override
                public int getNewListSize() {
                    return postCommentList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return PostCommentAdapter.this.postCommentList.get(oldItemPosition).id == postCommentList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    PostComment postComment = postCommentList.get(newItemPosition);
                    PostComment old = postCommentList.get(oldItemPosition);
                    return postComment.id == old.id && Objects.equals(postComment.postId, old.postId);
                }
            });
            this.postCommentList = postCommentList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public PostCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PostCommentListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.post_comment_list_item,parent, false);
        return new PostCommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PostCommentViewHolder holder, int position) {
        holder.binding.setPostcomment(postCommentList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return postCommentList == null ? 0 : postCommentList.size();
    }

    static class PostCommentViewHolder extends RecyclerView.ViewHolder {

        final PostCommentListItemBinding binding;

        public PostCommentViewHolder(PostCommentListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
