package com.example.e_voting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VoterID_Details extends AppCompatActivity {

    ImageView ivIdDetails_BackArrow, ivCNIC;
    TextView tvUploadCNIC;
    EditText etCNIC_Number;
    Button btnNext_VoterID_Details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voter_id_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        btnNext_VoterID_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VoterID_Details.this, VoterID_Verification.class));
            }
        });
    }

    private void init()
    {
        tvUploadCNIC = findViewById(R.id.tvUplaodCNIC);
        ivIdDetails_BackArrow = findViewById(R.id.ivIdDetails_BackArrow);
        ivCNIC = findViewById(R.id.ivCNIC);
        etCNIC_Number = findViewById(R.id.etCNIC_Number);
        btnNext_VoterID_Details = findViewById(R.id.btnNext_VoterID_Details);
    }
}