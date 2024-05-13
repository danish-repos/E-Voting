package com.example.e_voting.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.e_voting.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    RadioGroup radioGroup;
    EditText etFirstName , etLastName,  etEmail, etPassword, etConfirmPassword, etCNICR;
    TextView tvLogin;
    int gender =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.btnMale)
                    gender = 1;
                else if (checkedId == R.id.btnFemale)
                    gender = 2;
                else
                    gender = -1;

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();
                String rePassword = etConfirmPassword.getText().toString();
                String cnic = etCNICR.getText().toString().trim();

                if (  firstName.isEmpty() || lastName.isEmpty() ||email.isEmpty() || password.isEmpty() || rePassword.isEmpty()
                || cnic.isEmpty() ){
                    Toast.makeText(RegisterActivity.this, "Please fill out the form first", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(cnic.length() !=13){
                    Toast.makeText(RegisterActivity.this, "Please enter the correct cnic", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(rePassword)){
                    Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    return;
                }

                String userGender = "";
                if(gender != -1) {
                    if(gender == 1){
                        userGender += "male";
                    }
                    else if(gender == 2){
                        userGender += "female";
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterUser(firstName, lastName, email , password, userGender, cnic);
                finish();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void init(){

        btnRegister = findViewById(R.id.btnRegister);
        radioGroup = findViewById(R.id.radioGroup);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etCNICR = findViewById(R.id.etCNICR);

        tvLogin= findViewById(R.id.tvLogin);

    }

    public void RegisterUser(String firstName, String lastName, String email, String password, String gender, String cnic){

        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean present = false;

                        for (DataSnapshot data : snapshot.getChildren()) {
                            if (email.equals(data.child("Email").getValue(String.class)) || cnic.equals(data.child("Cnic").getValue(String.class))){

                                present = true;
                                break;
                            }
                        }

                        if (!present) {

                            HashMap<String, Object> singleLogin = new HashMap<>();
                            singleLogin.put("FirstName", firstName);
                            singleLogin.put("LastName", lastName);
                            singleLogin.put("Email", email);
                            singleLogin.put("Password", password);
                            singleLogin.put("Gender", gender);
                            singleLogin.put("Cnic", cnic);
                            singleLogin.put("isLogin", false);
                            singleLogin.put("isAdmin", false);

                            FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(singleLogin)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(RegisterActivity.this, "Registered!", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                        }
                        else {
                            Toast.makeText( RegisterActivity.this, "Already Present!", Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}