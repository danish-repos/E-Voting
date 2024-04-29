package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etName;
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
                name = etName.getText().toString().trim();
                password = etPassword.getText().toString().trim();


                if(email.length() == 0 || name.length() == 0 || password.length()==0)
                {
                    Toast.makeText(getApplicationContext(), R.string.you_can_t_leave_anything_empty,Toast.LENGTH_SHORT).show();
                    return;
                }

                if (radioButton == 0)
                {
                    Toast.makeText(getApplicationContext(), R.string.you_have_to_agree_to_the_terms_and_conditions_first ,Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);

                radioButton = 0;

                int result = loginUser(email,password);
                if (result <= 0)
                {
                    Toast.makeText(LogInActivity.this, "Invalid mail or password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                finish();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,RegisterActivity.class));
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
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);

        btnTermsAndConditions = findViewById(R.id.btnTermsAndConditions);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private int loginUser(String email,String password){
        MyDatabase database = new MyDatabase(this);

        database.open();
        int result = database.loginUser(email,password);
        database.close();

        return result;
    }
}