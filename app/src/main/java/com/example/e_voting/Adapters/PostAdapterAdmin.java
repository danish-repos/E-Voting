package com.example.e_voting.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.Classes.Post;
import com.example.e_voting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class PostAdapterAdmin extends FirebaseRecyclerAdapter<Post, PostAdapterAdmin.ViewHolder> {

    Context context;
    public PostAdapterAdmin(@NonNull FirebaseRecyclerOptions<Post> options, Context c) {
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

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Confirmation")
                        .setTitle("Do you really want to delete this post?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getRef(i).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                return false;
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_of_candidate,parent,false);
        return new PostAdapterAdmin.ViewHolder(v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
