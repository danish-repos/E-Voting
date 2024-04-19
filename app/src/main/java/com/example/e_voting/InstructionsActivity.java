package com.example.e_voting;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class InstructionsActivity extends AppCompatActivity {

    ImageView imgRegister;
    TextView tvRegisterInfo;
    TextView tvRegister;
    Button btnNext;
    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        init();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;

                if (clickCount == 1) {

                    // On the first click, change texts and pics
                    tvRegister.setText(R.string.vote);
                    tvRegisterInfo.setText(R.string.VoteInfo);
                    imgRegister.setImageResource(R.drawable.vote_splash);

                } else if (clickCount == 2) {

                    // On the second click, start the loginScreen activity
                    Intent intent = new Intent(InstructionsActivity.this, LogInActivity.class);
                    startActivity(intent);

                    // Reset clickCount to allow the cycle to repeat
                    clickCount = 0;

                    // Finish the Instructions Activity
                    finish();
                }
            }
        });
    }

    private void init() {
        imgRegister = findViewById(R.id.imgRegister);
        tvRegister = findViewById(R.id.tvRegister);
        tvRegisterInfo = findViewById(R.id.tvRegisterInfo);
        btnNext = findViewById(R.id.btnNext);
    }
}
