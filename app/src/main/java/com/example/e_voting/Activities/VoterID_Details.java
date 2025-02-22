package com.example.e_voting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.e_voting.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VoterID_Details extends AppCompatActivity {

    ImageView ivIdDetails_BackArrow, ivCNIC;
    TextView tvUploadCNIC;
    EditText etCNIC_Number;
    Button btnNext_VoterID_Details;
    final int REQUEST_IMAGE_PICK = 1;
    String Name, PartyName, nameOfLoggedInUser, cnicLoggedIn;

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

        Name = getIntent().getStringExtra("Name");
        PartyName = getIntent().getStringExtra("PartyName");
        nameOfLoggedInUser = getIntent().getStringExtra("UserName");

        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    if(data.child("isLogin").getValue(Boolean.class)){
                        cnicLoggedIn = data.child("Cnic").getValue(String.class);
                        etCNIC_Number.setText(cnicLoggedIn);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        btnNext_VoterID_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cnic = etCNIC_Number.getText().toString().trim();

                if(cnic.isEmpty()) {
                    Toast.makeText(VoterID_Details.this, "Enter CNIC Please", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(cnic.length() != 13){
                    Toast.makeText(VoterID_Details.this, "Please enter the correct CNIC", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(VoterID_Details.this, VoterID_Verification.class);
                intent.putExtra("Name", Name);
                intent.putExtra("PartyName", PartyName);
                intent.putExtra("CNIC", cnic);
                intent.putExtra("UserName", nameOfLoggedInUser);

                startActivity(intent);
            }
        });

        ivIdDetails_BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvUploadCNIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                //String extractedText = extractTextFromImage(bitmap);

                //Log.d("Extracted Text","Extracted Text " + extractedText);

                ivCNIC.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String extractTextFromImage(Bitmap bitmap) {
        TessBaseAPI tessBaseAPI = new TessBaseAPI();


        String dataPath = getApplicationContext().getFilesDir().getAbsolutePath();

        tessBaseAPI.init(dataPath, "eng");

        tessBaseAPI.setImage(bitmap);

        String extractedText = tessBaseAPI.getUTF8Text();

        tessBaseAPI.end();

        return extractedText;
    }

}