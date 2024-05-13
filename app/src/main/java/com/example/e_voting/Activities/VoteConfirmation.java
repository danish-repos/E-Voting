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

import com.example.e_voting.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class VoteConfirmation extends AppCompatActivity {

    ImageView ivVote_Confirmation_BackArrow, ivCandidate_Elect_Mark_C, ivCandidate_Photo_C;
    TextView tvCandidateName, tvCandidateParty_C;
    Button btnVote_Confirmation;
    DatabaseReference reference;

    String  CandidateID, Name, PartyName,CNIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vote_confirmation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        reference = FirebaseDatabase.getInstance().getReference();

        CandidateID = getCandidateID();

        init();


        Name = getIntent().getStringExtra("Name");
        PartyName = getIntent().getStringExtra("PartyName");
        CNIC = getIntent().getStringExtra("CNIC");

        tvCandidateName.setText(Name);
        tvCandidateParty_C.setText(PartyName);

        btnVote_Confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(VoteConfirmation.this, VoteReceipt.class);
                intent.putExtra("Name", Name);
                intent.putExtra("PartyName", PartyName);

                // firebase code
                StoreVoteOfUser();
                increaseCountofVotes_Candidate();

                startActivity(intent);
                finishAffinity();

            }
        });

        ivVote_Confirmation_BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void StoreVoteOfUser() {

        if(!CNIC.isEmpty() && !CandidateID.isEmpty()){

            String VoteID = reference.child("Votes").push().getKey();
            if (VoteID != null) {
                HashMap<String, Object> votedata = new HashMap<>();
                votedata.put("Cnic", CNIC);
                votedata.put("candidateID", CandidateID);


                reference.child("Votes")
                        .child(VoteID)
                        .setValue(votedata);
            }

        }
        else
            Toast.makeText(this, "Can't get ID", Toast.LENGTH_SHORT).show();


    }

    public String getCandidateID() {

        reference.child("Candidates").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {

                    if (Name.equals(data.child("name").getValue(String.class))
                            && PartyName.equals(data.child("partyName").getValue(String.class)))
                    {
                        CandidateID = data.getKey();
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return CandidateID;

    }

    public void increaseCountofVotes_Candidate(){
        if(!CandidateID.isEmpty()) {

            reference.child("Candidates")
                    .child(CandidateID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int current_count = snapshot.child("totalVotes").getValue(Integer.class);

                            reference.child("Candidates")
                                    .child(CandidateID)
                                    .child("totalVotes")
                                    .setValue(current_count + 1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }
        else
            Toast.makeText(this, "Can't get Candidate ID", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        tvCandidateName = findViewById(R.id.tvCandidateName_C);
        tvCandidateParty_C = findViewById(R.id.tvCandidateParty_C);
        ivVote_Confirmation_BackArrow = findViewById(R.id.ivVote_Confirmation_BackArrow);
        ivCandidate_Elect_Mark_C = findViewById(R.id.ivCandidate_Elect_Mark_C);
        ivCandidate_Photo_C = findViewById(R.id.ivCandidate_Photo_C);
        btnVote_Confirmation = findViewById(R.id.btnVote_Confirmation);
    }
}