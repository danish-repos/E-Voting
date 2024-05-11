package com.example.e_voting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.e_voting.R;

public class VoteReceipt extends AppCompatActivity {

    ImageView ivCandidate_Elect_Mark_R, ivCandidate_Photo_R;
    TextView tvCandidateName_R, tvCandidateParty_R, tvDownloadReceipt;
    Button btnBack_to_HomeScreen;
    String Name, PartyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vote_receipt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        Name = getIntent().getStringExtra("Name");
        PartyName = getIntent().getStringExtra("PartyName");

        tvCandidateName_R.setText(Name);
        tvCandidateParty_R.setText(PartyName);

        btnBack_to_HomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VoteReceipt.this, MainActivity.class));
                finish();
            }
        });



    }

    private void init() {

        ivCandidate_Elect_Mark_R = findViewById(R.id.ivCandidate_Elect_Mark_R);
        ivCandidate_Photo_R = findViewById(R.id.ivCandidate_Photo_R);
        tvCandidateName_R = findViewById(R.id.tvCandidateName_R);
        tvCandidateParty_R = findViewById(R.id.tvCandidateParty_R);
        tvDownloadReceipt = findViewById(R.id.tvDownloadReceipt);
        btnBack_to_HomeScreen = findViewById(R.id.btnBack_to_HomeScreen);
    }
}