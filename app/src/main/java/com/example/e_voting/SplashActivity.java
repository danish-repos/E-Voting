package com.example.e_voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IfAlreadyLoggedIn();
            }
        }, SPLASH_TIMEOUT);

    }

    public void IfAlreadyLoggedIn()
    {
        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        boolean found = false;
                        for (DataSnapshot data : snapshot.getChildren())
                        {
                            if (Boolean.TRUE.equals(data.child("isLogin").getValue(Boolean.class))) {
                                found= true;


                                if(Boolean.TRUE.equals(data.child("isAdmin").getValue(Boolean.class)))
                                {
                                    startActivity(new Intent(SplashActivity.this, AdminActivity.class));
                                    finish();
                                }
                                else{
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();

                                }
                                break;
                            }
                        }

                        if(!found){
                            startActivity(new Intent( SplashActivity.this,InstructionsActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}