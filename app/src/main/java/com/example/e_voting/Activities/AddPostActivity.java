package com.example.e_voting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.e_voting.Classes.Post;
import com.example.e_voting.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class AddPostActivity extends AppCompatActivity {

    TextInputEditText etTopicAP, etTextAP;
    Button btnAddNewPostAddP;

    ImageView ivBackAddP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnAddNewPostAddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = getIntent().getStringExtra("Name");
                String topic = etTopicAP.getText().toString().trim();
                String text = etTextAP.getText().toString().trim();

                if(topic.isEmpty() || text.isEmpty()){
                    Toast.makeText(AddPostActivity.this, "Can't leave empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Post post = new Post(name, topic, text, 0, 0);
                FirebaseDatabase.getInstance().getReference().child("Posts").push().setValue(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddPostActivity.this, "Added!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        ivBackAddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init(){
        etTopicAP = findViewById(R.id.etTopicAP);
        etTextAP = findViewById(R.id.etTextAP);
        btnAddNewPostAddP = findViewById(R.id.btnAddPostAddP);
        ivBackAddP = findViewById(R.id.ivBackAddP);

    }
}