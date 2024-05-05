package com.example.e_voting.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.e_voting.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LogInActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    RadioButton btnTermsAndConditions;
    Button btnSignIn, btnRegister;

    int radioButton = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,name,password;
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();


                if(email.length() == 0 || password.length()==0)
                {
                    Toast.makeText(getApplicationContext(), R.string.you_can_t_leave_anything_empty,Toast.LENGTH_SHORT).show();
                    return;
                }

                if (radioButton == 0)
                {
                    Toast.makeText(getApplicationContext(), R.string.you_have_to_agree_to_the_terms_and_conditions_first ,Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginUser(email,password);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });

        btnTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton = 1;
            }
        });
    }

    private void init()
    {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnTermsAndConditions = findViewById(R.id.btnTermsAndConditions);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);

    }


    public void LoginUser(String email, String password){
        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found = false;
                for (DataSnapshot data : snapshot.getChildren())
                {
                    if (email.equals(data.child("Email").getValue(String.class)) && password.equals(data.child("Password").getValue(String.class))) {

                        String userKey = data.getKey();
                        HashMap<String, Object> updateLoginStatus = new HashMap<>();
                        updateLoginStatus.put("isLogin", true);
                        found = true;

                        FirebaseDatabase.getInstance().getReference().child("Users").child(userKey).updateChildren(updateLoginStatus)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused)
                                    {
                                        if(Boolean.TRUE.equals(data.child("isAdmin").getValue(Boolean.class)))
                                        {
                                            startActivity(new Intent(LogInActivity.this, AdminActivity.class));
                                            finish();

                                        }
                                        else{
                                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                            finish();
                                        }

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LogInActivity.this, "Failed to Update Login Status", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        break;
                    }
                }
                if (!found) {
                    Toast.makeText(LogInActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}