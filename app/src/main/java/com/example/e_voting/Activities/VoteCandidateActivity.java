package com.example.e_voting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.e_voting.Classes.LinearLayoutManagerWrapper;
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

    String LogedInUserID, Name, PartyName;
    Boolean UseralreadyVoted;



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

        LogedInUserID = getLoggedInUser();

        init();

        UseralreadyVoted = CheckIFAlreadyVoted();


        Name = getIntent().getStringExtra("Name");
        PartyName = getIntent().getStringExtra("PartyName");

        rvPostsVC.setLayoutManager(new LinearLayoutManagerWrapper(this));
        rvPostsVC.setHasFixedSize(true);

        Query query = FirebaseDatabase.getInstance().getReference().child("Posts")
                .orderByChild("nameCandidate").equalTo(Name);

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();


        myAdapter = new PostAdapter(options,this);
        rvPostsVC.setAdapter(myAdapter);

        tvCandidateNameVC.setText(Name);
        tvCandidatePartyVC.setText(PartyName);

        btnVoteVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!UseralreadyVoted) {
                    Intent intent = new Intent(VoteCandidateActivity.this, VoterID_Details.class);
                    intent.putExtra("Name", Name);
                    intent.putExtra("PartyName", PartyName);

                    startActivity(intent);
                }
                else{
                    Toast.makeText(VoteCandidateActivity.this, "You have Already voted", Toast.LENGTH_SHORT).show();
                }




            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean CheckIFAlreadyVoted() {
        UseralreadyVoted = false;
        FirebaseDatabase.getInstance().getReference()
                .child("Votes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data: snapshot.getChildren()) {

                            String users_id = data.child("userID").getValue(String.class);
                            if (users_id != null) {
                                if (users_id.equals(LogedInUserID)) {
                                    UseralreadyVoted = true;
                                    break;
                                }

                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return UseralreadyVoted;
    }

    public String getLoggedInUser() {


        FirebaseDatabase.getInstance().getReference()
                .child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {

                    if(Boolean.TRUE.equals(data.child("isLogin").getValue(Boolean.class)))
                    {
                        LogedInUserID = data.getKey();
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return LogedInUserID;
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