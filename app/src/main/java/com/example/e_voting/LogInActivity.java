package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
    Button btnSignIn;

    int radioButton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        init();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,name,password;
                email = etEmail.getText().toString().trim();
                name = etName.getText().toString().trim();
                password = etPassword.getText().toString().trim();


                // some constraints checking

                // in case user left something empty
                if(email.length() == 0 || name.length() == 0 || password.length()==0)
                {
                    Toast.makeText(getApplicationContext(), R.string.you_can_t_leave_anything_empty,Toast.LENGTH_SHORT).show();
                    return;
                }

                // email text doesn't have the "@" in it
                if(!email.contains("@"))
                {
                    Toast.makeText(getApplicationContext(), R.string.not_a_valid_email,Toast.LENGTH_SHORT).show();
                    return;
                }

                // in case user type a password which is less then 8 characters long
                if(password.length() < 8)
                {
                    Toast.makeText(getApplicationContext(),R.string.shortPassword,Toast.LENGTH_SHORT).show();
                    return;
                }

                // if the radio button is not checked
                if (radioButton == 0)
                {
                    Toast.makeText(getApplicationContext(), R.string.you_have_to_agree_to_the_terms_and_conditions_first ,Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                startActivity(intent);

                // to allow the cycle to repeat
                radioButton = 0;

                finish();

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
        // to bold some certain text, i used html tags
        btnTermsAndConditions.setText(Html.fromHtml(getString(R.string.TermsAndConditions)));

        btnSignIn = findViewById(R.id.btnSignIn);
    }
}