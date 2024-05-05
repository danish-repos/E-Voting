package com.example.e_voting.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.e_voting.Classes.Candidate;
import com.example.e_voting.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddCandidateActivity extends AppCompatActivity {

    TextInputEditText etCandidateNameAC, etLocationAC, etPartyNameAC, etTypeAC;

    ImageView ivBackAC;
    Button btnAddCandidate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_candidate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnAddCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etCandidateNameAC.getText().toString().trim();
                String location = etLocationAC.getText().toString().trim();
                String partyName = etPartyNameAC.getText().toString().trim();
                String type = etTypeAC.getText().toString().trim();

                if(name.isEmpty() || partyName.isEmpty() || type.isEmpty() || location.isEmpty())
                {
                    Toast.makeText(AddCandidateActivity.this, "Can't leave empty ", Toast.LENGTH_SHORT).show();
                    return;
                }

                Candidate candidate = new Candidate(name, location, type, partyName, 0);
                FirebaseDatabase.getInstance().getReference().child("Candidates").push().setValue(candidate)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddCandidateActivity.this, "Added!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });


            }
        });

        ivBackAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        etCandidateNameAC = findViewById(R.id.etCandidateNameAC);
        etLocationAC = findViewById(R.id.etLocationAC);
        etPartyNameAC = findViewById(R.id.etPartyNameAC);
        ivBackAC = findViewById(R.id.ivBackAC);
        etTypeAC = findViewById(R.id.etTypeAC);

        btnAddCandidate = findViewById(R.id.btnAddCandidate);


    }
}