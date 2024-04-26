package com.example.e_voting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    RadioGroup radioGroup;
    EditText etFirstName , etLastName,  etEmail, etPassword, etConfirmPassword;
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

                if (  firstName.isEmpty() || lastName.isEmpty() ||email.isEmpty() || password.isEmpty() || rePassword.isEmpty() ){
                    Toast.makeText(RegisterActivity.this, "Please fill out the form first", Toast.LENGTH_SHORT).show();
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
                registerUser(firstName,lastName,email,password,userGender);

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

        tvLogin= findViewById(R.id.tvLogin);

    }

    private  void registerUser(String firstName, String lastName, String email, String password, String gender){
        MyDatabase database = new MyDatabase(this);

        database.open();
        database.insertLogin(firstName,lastName,email,password,gender);
        database.close();
    }
}