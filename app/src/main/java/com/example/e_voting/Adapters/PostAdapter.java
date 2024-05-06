package com.example.e_voting.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.Classes.Post;
import com.example.e_voting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostAdapter.ViewHolder> {

    Context context;
    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options, Context c) {
        super(options);
        context = c;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Post post) {

        viewHolder.tvCandidateNameP.setText(post.getNameCandidate());
        viewHolder.tvTopic_Post.setText(post.getTopic());
        viewHolder.tvPost.setText(post.getText());
        viewHolder.tvLike.setText(post.getLikes()+" upvotes");
        viewHolder.tvDislike.setText(post.getDislikes()+" downvotes");



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_of_candidate,parent,false);
        return new PostAdapter.ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTopic_Post, tvPost, tvLike, tvDislike, tvCandidateNameP;
        ImageView ivLike, ivDislike;
        LinearLayout llLike, llDislike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTopic_Post = itemView.findViewById(R.id.tvTopic_Post);
            tvPost = itemView.findViewById(R.id.tvPost);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvDislike = itemView.findViewById(R.id.tvDislike);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivDislike = itemView.findViewById(R.id.ivDislike);

            tvCandidateNameP = itemView.findViewById(R.id.tvCandidateNameP);

            llLike = itemView.findViewById(R.id.llLike);
            llDislike = itemView.findViewById(R.id.llDislike);

        }
    }
}
