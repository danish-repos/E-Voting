package com.example.e_voting;

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

import org.w3c.dom.Text;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;

    public PostAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTopic_Post, tvPost, tvLike, tvDislike, tvComment;
        ImageView ivLike, ivDislike, ivComment;
        LinearLayout llLike, llDislike, llComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTopic_Post = itemView.findViewById(R.id.tvTopic_Post);
            tvPost = itemView.findViewById(R.id.tvPost);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvDislike = itemView.findViewById(R.id.tvDislike);
            tvComment = itemView.findViewById(R.id.tvComment);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivDislike = itemView.findViewById(R.id.ivDislike);
            ivComment = itemView.findViewById(R.id.ivComment);

            llLike = itemView.findViewById(R.id.llLike);
            llDislike = itemView.findViewById(R.id.llDislike);
            llComment = itemView.findViewById(R.id.llComment);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_post_of_candidate, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.llLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ivLike.setColorFilter(Color.argb(0, 0, 0, 139));
                holder.ivLike.setColorFilter(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                holder.tvLike.setTextColor(ContextCompat.getColor(context, R.color.DarkPrimaryColor));

                String temp = holder.tvLike.getText().toString().trim();
                String likes[] = temp.split(" ");
                int like = Integer.parseInt(likes[0]);
                like += 1;
                temp = like + " " + likes[1];

                holder.tvLike.setText(temp);
            }
        });

        holder.llDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ivLike.setColorFilter(Color.argb(0, 0, 0, 139));
                holder.ivDislike.setColorFilter(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                holder.tvDislike.setTextColor(ContextCompat.getColor(context, R.color.DarkPrimaryColor));

                String temp = holder.tvDislike.getText().toString().trim();
                String dislikes[] = temp.split(" ");
                int dislike = Integer.parseInt(dislikes[0]);
                dislike += 1;
                temp = dislike + " " + dislikes[1];

                holder.tvDislike.setText(temp);
            }
        });

        holder.llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ivLike.setColorFilter(Color.argb(0, 0, 0, 139));
                holder.ivComment.setColorFilter(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                holder.tvComment.setTextColor(ContextCompat.getColor(context, R.color.DarkPrimaryColor));

            }
        });

    }



    @Override
    public int getItemCount() {
        return 0;
    }
}
