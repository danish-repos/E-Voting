package com.example.e_voting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.Adapters.PostAdapter;


import com.example.e_voting.Classes.Candidate;
import com.example.e_voting.Classes.Post;
import com.example.e_voting.R;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class VoteCandidateActivity extends AppCompatActivity {

    Button btnVoteVC;
    RecyclerView rvPostsVC;
    PostAdapter myAdapter;
    ImageView imgBack;

    TextView tvCandidateNameVC, tvCandidatePartyVC;

    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vote_candidate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();


        name = getIntent().getStringExtra("Name");

        rvPostsVC.setLayoutManager(new LinearLayoutManager(this));
        rvPostsVC.setHasFixedSize(true);

        Query query = FirebaseDatabase.getInstance().getReference().child("Posts")
                .orderByChild("nameCandidate").equalTo(name);

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();


        myAdapter = new PostAdapter(options,this);
        rvPostsVC.setAdapter(myAdapter);

        Query query1 = FirebaseDatabase.getInstance().getReference().child("Candidates").orderByChild("name").equalTo(name);

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot data : snapshot.getChildren()) {
                        Candidate candidate = data.getValue(Candidate.class);
                        tvCandidateNameVC.setText(candidate.getName());
                        tvCandidatePartyVC.setText(candidate.getPartyName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnVoteVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VoteCandidateActivity.this, VoterID_Details.class));


            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        btnVoteVC = findViewById(R.id.btnVoteVC);
        imgBack = findViewById(R.id.imgBack);

        rvPostsVC = findViewById(R.id.rvPostsVC);
        tvCandidateNameVC = findViewById(R.id.tvCandidateNameVC);
        tvCandidatePartyVC = findViewById(R.id.tvCandidatePartyVC);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }
}