package com.example.e_voting;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class InstructionsActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment pageOne, pageTwo;
    Button btnNext, btnNext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        init();

        if(isUserLoggedIn()){
            startActivity(new Intent(InstructionsActivity.this, MainActivity.class));
            finish();
        }

        fragmentManager.beginTransaction().hide(pageTwo).commit();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().show(pageTwo).hide(pageOne).commit();
                }

        });

        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstructionsActivity.this,LogInActivity.class));
            }
        });

    }

    private void init() {

        fragmentManager = getSupportFragmentManager();
        pageOne = fragmentManager.findFragmentById(R.id.instructions1);
        pageTwo = fragmentManager.findFragmentById(R.id.instructions2);

        btnNext = pageOne.requireView().findViewById(R.id.btnNext);
        btnNext2 = pageTwo.requireView().findViewById(R.id.btnNext2);

    }

    private boolean isUserLoggedIn(){
        MyDatabase database = new MyDatabase(this);

        database.open();
        boolean temp = database.isUserLoggedIn();
        database.close();

        return temp;

    }
}
