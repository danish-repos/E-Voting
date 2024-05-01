package com.example.e_voting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VoteConfirmation extends AppCompatActivity {

    ImageView ivVote_Confirmation_BackArrow, ivCandidate_Elect_Mark_C, ivCandidate_Photo_C;
    TextView tvCandidateName, tvCandidateParty_C;
    Button btnVote_Confirmation;

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

        init();
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