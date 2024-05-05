package com.example.e_voting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.Adapters.CandidateAdapterAdmin;
import com.example.e_voting.Classes.Candidate;
import com.example.e_voting.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    RecyclerView rvCandidateList;
    Button btnAddCandidate;

    TextView tvAdmin;
    CandidateAdapterAdmin myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnAddCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddCandidateActivity.class));

            }
        });

        tvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(AdminActivity.this).inflate(R.layout.admin_logout_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                Button btnLogout = dialogView.findViewById(R.id.btnLogout);
                TextView tvName = dialogView.findViewById(R.id.tvName);

                FirebaseDatabase.getInstance().getReference().child("Users")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String loggedInUserName = null;
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    Boolean isLogin = data.child("isLogin").getValue(Boolean.class);
                                    if (isLogin != null && isLogin) {
                                        loggedInUserName = data.child("FirstName").getValue(String.class);
                                        break;
                                    }
                                }
                                tvName.setText(loggedInUserName);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle database error
                            }
                        });

                btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LogoutUser();

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

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

    private void init(){
        rvCandidateList = findViewById(R.id.rvCandidateList);
        btnAddCandidate = findViewById(R.id.btnAddCandidate);

        tvAdmin = findViewById(R.id.tvAdmin);

        rvCandidateList.setLayoutManager(new LinearLayoutManager(this));
        rvCandidateList.setHasFixedSize(true);

        Query query = FirebaseDatabase.getInstance().getReference().child("Candidates");

        FirebaseRecyclerOptions<Candidate> options =
                new FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate.class).build();


        myAdapter = new CandidateAdapterAdmin(options, this);
        rvCandidateList.setAdapter(myAdapter);


    }

    private void LogoutUser(){

        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot data : snapshot.getChildren())
                        {
                            if (Boolean.TRUE.equals(data.child("isLogin").getValue(Boolean.class)))
                            {
                                String userKey = data.getKey();
                                HashMap<String, Object> updateLoginStatus = new HashMap<>();
                                updateLoginStatus.put("isLogin", false);

                                FirebaseDatabase.getInstance().getReference().child("Users").child(userKey).updateChildren(updateLoginStatus)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                startActivity(new Intent(AdminActivity.this, LogInActivity.class));
                                                finish();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}