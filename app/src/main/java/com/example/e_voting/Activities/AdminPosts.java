package com.example.e_voting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.Adapters.PostAdapter;
import com.example.e_voting.Adapters.PostAdapterAdmin;
import com.example.e_voting.Classes.LinearLayoutManagerWrapper;
import com.example.e_voting.Classes.Post;
import com.example.e_voting.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminPosts extends AppCompatActivity {

    RecyclerView rvPostsAdmin;
    PostAdapterAdmin myAdapter;

    ImageView ivBackAP;
    Button btnAddNewPostAP;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_posts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = getIntent().getStringExtra("Name");
        init();

        btnAddNewPostAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPosts.this, AddPostActivity.class);
                intent.putExtra("Name",name);
                startActivity(intent);
            }
        });

        ivBackAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    private void init(){
        ivBackAP= findViewById(R.id.ivBackAP);
        btnAddNewPostAP= findViewById(R.id.btnAddNewPostAP);

        rvPostsAdmin = findViewById(R.id.rvPostsAdmin);

        rvPostsAdmin.setLayoutManager(new LinearLayoutManagerWrapper(this,LinearLayoutManager.VERTICAL, false));
        rvPostsAdmin.setHasFixedSize(true);

        Query query = FirebaseDatabase.getInstance().getReference().child("Posts")
                .orderByChild("nameCandidate").equalTo(name);

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();


        myAdapter = new PostAdapterAdmin(options,this);
        rvPostsAdmin.setAdapter(myAdapter);
    }
}