package com.example.e_voting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.e_voting.R;

public class VoterID_Verification extends AppCompatActivity {

    ImageView ivIdVerification_BackArrow;
    EditText etVoterName, etVoterDOB, etVoterIdNumber, etVoterAddress, etZipcode;
    Button btnConfirm_VoterID_Verification;

    String Name, PartyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voter_id_verification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        Name = getIntent().getStringExtra("Name");
        PartyName = getIntent().getStringExtra("PartyName");

        btnConfirm_VoterID_Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VoterID_Verification.this, VoteConfirmation.class);
                intent.putExtra("Name", Name);
                intent.putExtra("PartyName", PartyName);

                startActivity(intent);

            }
        });

        ivIdVerification_BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        ivIdVerification_BackArrow = findViewById(R.id.ivIdVerification_BackArrow);
        etVoterName = findViewById(R.id.etVoterName);
        etVoterDOB = findViewById(R.id.etVoterDOB);
        etVoterIdNumber = findViewById(R.id.etVoterIdNumber);
        etVoterAddress = findViewById(R.id.etVoterAddress);
        etZipcode = findViewById(R.id.etZipcode);
        btnConfirm_VoterID_Verification = findViewById(R.id.btnConfirm_VoterID_Verification);

    }
}