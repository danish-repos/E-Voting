package com.example.e_voting.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.Classes.Post;
import com.example.e_voting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostAdapter.ViewHolder> {

    Context context;
    String User;
    DatabaseReference reference;
    private final String KEY_USER_INTERACTIONS = "UserInteractions";
    boolean User_AlreadyLiked, User_AlreadyDisliked;

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

        reference = FirebaseDatabase.getInstance().getReference();
        User = getLoggedInUser();

        SetStatusOfPosts_forCurrentUser(viewHolder, getRef(i).getKey());

        viewHolder.llLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_AlreadyLiked = CheckIF_UserAlreadyLiked(getRef(i).getKey());
                User_AlreadyDisliked = CheckIF_UserAlreadyDisliked(getRef(i).getKey());

                if (User_AlreadyLiked) {
                    remove_User_Like(getRef(i).getKey());

                    viewHolder.ivLike.clearColorFilter();
                    viewHolder.tvLike.setTextColor(ContextCompat.getColor(context, R.color.black));


                }
                else if (User_AlreadyDisliked) {
                    remove_User_Dislike(getRef(i).getKey());
                    User_Like_Post(getRef(i).getKey());

                    viewHolder.ivDislike.clearColorFilter();
                    viewHolder.tvDislike.setTextColor(ContextCompat.getColor(context, R.color.black));

                    viewHolder.ivLike.setColorFilter(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                    viewHolder.tvLike.setTextColor(ContextCompat.getColor(context, R.color.DarkPrimaryColor));

                }
                else {
                    User_Like_Post(getRef(i).getKey());

                    viewHolder.ivLike.setColorFilter(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                    viewHolder.tvLike.setTextColor(ContextCompat.getColor(context, R.color.DarkPrimaryColor));

                }

            }
        });

        viewHolder.llDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User_AlreadyLiked = CheckIF_UserAlreadyLiked(getRef(i).getKey());
                User_AlreadyDisliked = CheckIF_UserAlreadyDisliked(getRef(i).getKey());

                if (User_AlreadyLiked) {

                    remove_User_Like(getRef(i).getKey());
                    User_Dislike_Post(getRef(i).getKey());

                    viewHolder.ivLike.clearColorFilter();
                    viewHolder.tvLike.setTextColor(ContextCompat.getColor(context, R.color.black));

                    viewHolder.ivDislike.setColorFilter(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                    viewHolder.tvDislike.setTextColor(ContextCompat.getColor(context, R.color.DarkPrimaryColor));


                }
                else if (User_AlreadyDisliked) {
                    remove_User_Dislike(getRef(i).getKey());

                    viewHolder.ivDislike.clearColorFilter();
                    viewHolder.tvDislike.setTextColor(ContextCompat.getColor(context, R.color.black));

                }
                else {
                    User_Dislike_Post(getRef(i).getKey());

                    viewHolder.ivDislike.setColorFilter(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                    viewHolder.tvDislike.setTextColor(ContextCompat.getColor(context, R.color.DarkPrimaryColor));

                }
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_of_candidate,parent,false);
        return new PostAdapter.ViewHolder(v);
    }

    public boolean CheckIF_UserAlreadyLiked(String PostId) {
        if (User != null) {
            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostId)
                    .child(User)
                    .child("like").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User_AlreadyLiked = snapshot.getValue(Boolean.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
        return User_AlreadyLiked;

    }


    public boolean CheckIF_UserAlreadyDisliked(String PostId) {
        if (User != null) {
            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostId)
                    .child(User)
                    .child("dislike").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User_AlreadyDisliked = snapshot.getValue(Boolean.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
        return User_AlreadyDisliked;
    }

    public void User_Like_Post(String PostID){
        String UserID = User;

        if(UserID != null) {
            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostID)
                    .child(UserID)
                    .child("like").setValue(true);

            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostID)
                    .child(UserID)
                    .child("dislike").setValue(false);

        }else
            Toast.makeText(context, "Not Stored", Toast.LENGTH_SHORT).show();


        reference.child("Posts")
                .child(PostID)
                .child("likes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int curr_likes = snapshot.getValue(Integer.class);

                        reference.child("Posts")
                                .child(PostID)
                                .child("likes")
                                .setValue(curr_likes + 1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void remove_User_Like(String PostID) {
        String UserID = User;

        if(UserID != null) {
            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostID)
                    .child(UserID)
                    .child("like").setValue(false);

            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostID)
                    .child(UserID)
                    .child("dislike").setValue(false);

        }else
            Toast.makeText(context, "Not Stored", Toast.LENGTH_SHORT).show();


        reference.child("Posts")
                .child(PostID)
                .child("likes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int curr_likes = snapshot.getValue(Integer.class);

                        reference.child("Posts")
                                .child(PostID)
                                .child("likes")
                                .setValue(curr_likes - 1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void User_Dislike_Post(String PostID) {
        String UserID = User;

        if(UserID != null) {
            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostID)
                    .child(UserID)
                    .child("like").setValue(false);

            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostID)
                    .child(UserID)
                    .child("dislike").setValue(true);

        }else
            Toast.makeText(context, "Not Stored", Toast.LENGTH_SHORT).show();


        reference.child("Posts")
                .child(PostID)
                .child("dislikes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int curr_dislikes = snapshot.getValue(Integer.class);

                        reference.child("Posts")
                                .child(PostID)
                                .child("dislikes")
                                .setValue(curr_dislikes + 1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    public void remove_User_Dislike(String PostID){
        String UserID = User;

        if(UserID != null) {
            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostID)
                    .child(UserID)
                    .child("like").setValue(false);

            reference.child(KEY_USER_INTERACTIONS)
                    .child(PostID)
                    .child(UserID)
                    .child("dislike").setValue(false);

        }else
            Toast.makeText(context, "Not Stored", Toast.LENGTH_SHORT).show();


        reference.child("Posts")
                .child(PostID)
                .child("dislikes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int curr_dislikes = snapshot.getValue(Integer.class);

                        reference.child("Posts")
                                .child(PostID)
                                .child("dislikes")
                                .setValue(curr_dislikes - 1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public String getLoggedInUser() {


        reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {

                    if(Boolean.TRUE.equals(data.child("isLogin").getValue(Boolean.class)))
                    {
                        User = data.getKey();
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return User;
    }


    public void SetStatusOfPosts_forCurrentUser(ViewHolder viewHolder, String PostId) {
        reference.child(KEY_USER_INTERACTIONS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot users = snapshot.child(PostId).child(User);
                if (users.exists()) {

                    boolean like = users.child("like").getValue(Boolean.class);
                    boolean dislike = users.child("dislike").getValue(Boolean.class);

                    if (like == true && dislike == false) {
                        viewHolder.ivLike.setColorFilter(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                        viewHolder.tvLike.setTextColor(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                    } else if (dislike == true && like == false) {
                        viewHolder.ivDislike.setColorFilter(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                        viewHolder.tvDislike.setTextColor(ContextCompat.getColor(context, R.color.DarkPrimaryColor));
                    }

                }

           }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

            User_AlreadyLiked = false;
            User_AlreadyDisliked = false;

        }
    }
}
